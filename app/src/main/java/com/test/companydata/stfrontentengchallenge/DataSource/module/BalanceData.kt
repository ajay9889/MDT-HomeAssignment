package com.test.companydata.stfrontentengchallenge.DataSource.module
import com.google.gson.annotations.SerializedName

data class BalanceData(
    @SerializedName("status") val status: String?="",
    @SerializedName("accountNo") val accountNo: String?="",
    @SerializedName("accountHolder") var accountHolder: String?="",
    @SerializedName("balance") val balance: String?="",
    @SerializedName("error")
    val error: String?="",
)
