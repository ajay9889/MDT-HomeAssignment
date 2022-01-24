package com.test.companydata.stfrontentengchallenge.Core.Util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData

class UserSecurePreferences {
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
                    editor.putString("loginData" , Gson().toJson(data))
                    editor.commit()
                }
            }
        }
    }
}