package com.test.companydata.stfrontentengchallenge.DataSource.module

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserInfoRemoteData(
    @SerializedName("accountNo")
    val accountNo: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("username")
    var username: String?="",
    @SerializedName("error")
    val error: String?,
): Serializable{
    companion object{
        fun getDefaultUserData()=UserInfoRemoteData(
            accountNo = "",
            status = "",
            token = "",
            username = "",
            error = ""
        )
    }
}