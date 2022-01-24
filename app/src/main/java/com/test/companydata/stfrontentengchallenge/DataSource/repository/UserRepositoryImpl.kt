package com.test.companydata.stfrontentengchallenge.DataSource.repository

import android.content.Context
import com.test.companydata.ApiService.ApiEndpoints
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.Domain.repository.UserRepository
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import org.json.JSONObject
import retrofit2.Retrofit
class UserRepositoryImpl(val context: Context, val retrofit: Retrofit): UserRepository {
    override suspend fun getUserLogin(username: String, password: String): ViewState<UserInfoRemoteData> {
         retrofit.create(ApiEndpoints::class.java).getUserLogin(
            username,
            password
        ).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                when(it){
                    is retrofit2.HttpException -> {
                        it.response()?.errorBody()?.let {
                            return ViewState.Message(JSONObject(it.string()).getString("message"))
                        }
                    }
                    else -> { }
                }

            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }
    override suspend fun createNewAccount(username: String, password: String): ViewState<UserInfoRemoteData> {
        retrofit.create(ApiEndpoints::class.java).getUserLogin(
            username,
            password
        ).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                when(it){
                    is retrofit2.HttpException -> {
                        it.response()?.errorBody()?.let {
                            return ViewState.Message(JSONObject(it.string()).getString("message"))
                        }
                    }
                    else -> { }
                }

            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }
}