package com.test.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("sender")
    val sender: Sender?,
    @SerializedName("transactionDate")
    val transactionDate: String?,
    @SerializedName("transactionId")
    val transactionId: String?,
    @SerializedName("transactionType")
    val transactionType: String?
)