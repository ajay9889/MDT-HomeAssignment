package com.test.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName

data class TransactionData(
    @SerializedName("data")
    val `data`: List<DataX>?,
    @SerializedName("status")
    val status: String?,
    val error: String?,

)