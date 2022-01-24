package com.test.companydata.stfrontentengchallenge.DataSource.module

import com.google.gson.annotations.SerializedName

data class UserInfoRemoteData(
    @SerializedName("accountNo")
    val accountNo: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("error")
    val error: String?,
)