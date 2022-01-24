package com.test.companydata.stfrontentengchallenge.DataSource.module


import com.google.gson.annotations.SerializedName

data class PayeesData(
    @SerializedName("data")
    val `data`: List<Data>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("error")
    val error: String?,
)