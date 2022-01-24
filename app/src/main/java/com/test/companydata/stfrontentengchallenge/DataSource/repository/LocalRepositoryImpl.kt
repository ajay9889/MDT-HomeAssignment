package com.test.companydata.stfrontentengchallenge.DataSource.repository

import android.content.Context
import com.google.gson.Gson
import com.test.companydata.stfrontentengchallenge.Core.Util.UserSecurePreferences
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.Domain.repository.LocalRepository

class LocalRepositoryImpl(val context: Context): LocalRepository {
    override fun getUserAccountNo(): String? {
        UserSecurePreferences.getLoggedInUserData(context)?.let {
            if(it.length>0)
            {
                return Gson().fromJson(it, UserInfoRemoteData::class.java).accountNo
            }
        }
        return ""
    }

    override fun getJwtTokent(): String? {
        UserSecurePreferences.getLoggedInUserData(context)?.let {
            if(it.length>0)
            {
                return Gson().fromJson(it, UserInfoRemoteData::class.java).token
            }
        }
        return ""
    }
}