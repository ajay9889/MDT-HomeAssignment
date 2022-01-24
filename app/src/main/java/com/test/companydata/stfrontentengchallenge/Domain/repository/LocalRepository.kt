package com.test.companydata.stfrontentengchallenge.Domain.repository


import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState

interface LocalRepository {
    fun getUserAccountNo() : String?
    fun getJwtTokent() : String?
}