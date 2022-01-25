package com.test.companydata.stfrontentengchallenge.DataSource.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.test.companydata.ApiService.ApiEndpoints
import com.test.companydata.stfrontentengchallenge.Core.Util.UserSecurePreferences
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.Domain.repository.UserRepository
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import retrofit2.Retrofit
import okhttp3.RequestBody



class UserRepositoryImpl(val context: Context, val retrofit: Retrofit): UserRepository {
    override suspend fun getUserLoggedInInformations(): ViewState<UserInfoRemoteData> {
        UserSecurePreferences.getLoggedInUserData(context,UserSecurePreferences.loginData)?.let {
            Log.d("TAG" , "$it")
             if(it.length>0){
                   Gson().fromJson(it, UserInfoRemoteData::class.java)?.let {
                       Log.d("TAG_!" , "$it")
                       return ViewState.Content(it)
                 }
             }
        }
        return ViewState.Content(UserInfoRemoteData.getDefaultUserData())
    }

    override suspend fun getUserLogout(): ViewState<UserInfoRemoteData> {
        UserSecurePreferences.getUserLogout(context).let {
            return ViewState.Message("Success")
        }
    }

    override suspend fun getUserLogin(username: String, password: String): ViewState<UserInfoRemoteData> {
        val mime= "application/json; charset=utf-8".toMediaTypeOrNull();
        val body: RequestBody = RequestBody.create(
            mime,
            JSONObject().apply {
                this.put("username",username)
                this.put("password",password)
            }.toString()
        )

        retrofit.create(ApiEndpoints::class.java).getUserLogin(
            body
        ).let {
            if(it.isSuccessful){
                it.body()?.let {
                    UserSecurePreferences.saveUserInformationInLocalSecurePreferences(context ,it );
                    return ViewState.Content(it)
                }?: kotlin.run {
                    it.errorBody()?.let {
                        return ViewState.Message(JSONObject(it.string()).getString("error"))
                    }
                }
            }else{
                it.errorBody()?.let {
                    return ViewState.Message(JSONObject(it.string()).getString("error"))
                }
            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }
    override suspend fun createNewAccount(username: String, password: String): ViewState<UserInfoRemoteData> {
        val mime= "application/json; charset=utf-8".toMediaTypeOrNull();
        val body: RequestBody = RequestBody.create(
            mime,
            JSONObject().apply {
                this.put("username",username)
                this.put("password",password)
            }.toString()
        )
        retrofit.create(ApiEndpoints::class.java).createNewAccount(
            body
        ).let {
            if(it.isSuccessful){
                it.body()?.let {
                    UserSecurePreferences.saveUserInformationInLocalSecurePreferences(context ,it );
                    return ViewState.Content(it)
                }
            }else{
                it.errorBody()?.let {
                    return ViewState.Message(JSONObject(it.string()).getString("error"))
                }
            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }
}