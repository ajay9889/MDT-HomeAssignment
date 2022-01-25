package com.test.companydata.stfrontentengchallenge.Core.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData

object UserSecurePreferences {
    val sharedPrefsFile: String = "BANKING_USER_DATA"
    val loginData: String = "loginData"
    val sharedKeyAliase: String = "bank_account_info"
    fun getSecurePreferences(context: Context) = EncryptedSharedPreferences.create(
            sharedPrefsFile,
            sharedKeyAliase,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    fun saveUserInformationInLocalSecurePreferences(context: Context , data: UserInfoRemoteData){
        getSecurePreferences(context).let {
            it.edit().let { editor: SharedPreferences.Editor? ->
                editor?.let {
                    editor.putString(loginData , Gson().toJson(data))
                    editor.commit()
                }
            }
        }
    }

    fun getLoggedInUserData(context: Context ,aliase: String ):String?{
        return getSecurePreferences(context).getString(aliase, Gson().toJson(UserInfoRemoteData.getDefaultUserData()))
    }

    fun getUserLogout(context: Context){
        getSecurePreferences(context).let {
            it.edit().let { editor: SharedPreferences.Editor? ->
                editor?.let {
                    editor.clear()
                    editor.commit()
                }
            }
        }
    }
}