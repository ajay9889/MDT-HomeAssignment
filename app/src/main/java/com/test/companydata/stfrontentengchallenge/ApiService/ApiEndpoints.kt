package com.test.companydata.ApiService
import com.test.companydata.stfrontentengchallenge.DataSource.module.BalanceData
import com.test.companydata.stfrontentengchallenge.DataSource.module.PayeesData
import com.test.companydata.stfrontentengchallenge.DataSource.module.TransactionData
import com.test.companydata.stfrontentengchallenge.DataSource.module.UserInfoRemoteData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiEndpoints {

    // User Account
    @POST("register")
    suspend fun createNewAccount(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<UserInfoRemoteData>

    // POST request to get the user login
    @POST("login")
    suspend fun getUserLogin(
        @Query("username") username: String,
        @Query("password") password: String):
            Response<UserInfoRemoteData>


    // Get the User balances
    @GET("balance")
    suspend fun getUserBalances():Response<BalanceData>

    // Get the User transactions
    @GET("transactions")
    suspend fun getUserTransactions():Response<TransactionData>

    @GET("payees")
    suspend fun getPayees():Response<PayeesData>

    @GET("transactions")
    suspend fun getTransferBalance(
        @Query("recipientAccountNo") username: String,
        @Query("amount") amount: String,
        @Query("date") date: String,
        @Query("description") description: String,
    ):Response<BalanceData>
}