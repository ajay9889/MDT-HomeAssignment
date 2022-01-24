package com.test.companydata.stfrontentengchallenge.Domain.repository


import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState

interface UserRepository {
    suspend fun getUserLogin(username: String, password: String) : ViewState<UserInfoRemoteData>
    suspend fun createNewAccount(username: String, password: String) : ViewState<UserInfoRemoteData>
}