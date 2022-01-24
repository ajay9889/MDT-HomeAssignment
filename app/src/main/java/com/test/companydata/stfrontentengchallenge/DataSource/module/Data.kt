package com.test.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("accountNo")
    val accountNo: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?
)