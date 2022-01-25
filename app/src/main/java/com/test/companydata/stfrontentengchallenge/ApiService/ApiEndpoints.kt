package com.test.companydata.ApiService
import com.test.companydata.stfrontentengchallenge.DataSource.module.BalanceData
import com.test.companydata.stfrontentengchallenge.DataSource.module.PayeesData
import com.test.companydata.stfrontentengchallenge.DataSource.module.TransactionData
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiEndpoints {

    // User Account
    @POST("register")
    suspend fun createNewAccount(
        @Body params : RequestBody
    ): Response<UserInfoRemoteData>

    // POST request to get the user login
    @POST("login")
    suspend fun getUserLogin(
        @Body params : RequestBody
    ):Response<UserInfoRemoteData>


    // Get the User balances
    @GET("balance")
    suspend fun getUserBalances():Response<BalanceData>

    // Get the User transactions
    @GET("transactions")
    suspend fun getUserTransactions():Response<TransactionData>

    @GET("payees")
    suspend fun getPayees():Response<PayeesData>

    @POST("transfer")
    suspend fun getTransferBalance(
        @Body params : RequestBody
    ):Response<BalanceData>
}