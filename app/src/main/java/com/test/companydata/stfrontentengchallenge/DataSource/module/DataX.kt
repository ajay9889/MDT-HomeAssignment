package com.test.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("amount")
    val amount: Int?=0,
    @SerializedName("description")
    val description: String?,
    @SerializedName("sender")
    val sender: Sender?,
    @SerializedName("receipient")
    val receipient: Receipient?,
    @SerializedName("transactionDate")
    val transactionDate: String?,
    @SerializedName("transactionId")
    val transactionId: String?,
    @SerializedName("transactionType")
    val transactionType: String?
)


data class Receipient(
    @SerializedName("accountHolder")
    val accountHolder: String?,
    @SerializedName("accountNo")
    val accountNo: String?
)