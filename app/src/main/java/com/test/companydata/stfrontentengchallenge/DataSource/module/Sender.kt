package com.test.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName

data class Sender(
    @SerializedName("accountHolder")
    val accountHolder: String?,
    @SerializedName("accountNo")
    val accountNo: String?
)