package com.test.companydata.stfrontentengchallenge.DataSource.repository


import android.content.Context
import com.test.companydata.ApiService.ApiEndpoints
import com.test.companydata.stfrontentengchallenge.DataSource.module.BalanceData
import com.test.companydata.stfrontentengchallenge.DataSource.module.PayeesData
import com.test.companydata.stfrontentengchallenge.DataSource.module.TransactionData
import com.test.companydata.stfrontentengchallenge.Domain.repository.AccountDataRepository
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class AccountDataRepositoryImpl(val context: Context, val retrofit: Retrofit): AccountDataRepository {
    override suspend fun getAccountBalances(): ViewState<BalanceData> {
        retrofit.create(ApiEndpoints::class.java).getUserBalances().let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                it.errorBody()?.let {
                    return ViewState.Message(JSONObject(it.string()).getString("error"))
                }

            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }
    override suspend fun getPayeesList(): ViewState<PayeesData> {
        retrofit.create(ApiEndpoints::class.java).getPayees().let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                it.errorBody()?.let {
                    return ViewState.Message(JSONObject(it.string()).getString("error"))
                }
            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }

    override suspend fun getTransactionsList(): ViewState<TransactionData> {
        retrofit.create(ApiEndpoints::class.java).getUserTransactions().let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                it.errorBody()?.let {
                    return ViewState.Message(JSONObject(it.string()).getString("error"))
                }
            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }


    override suspend fun getTransferBalance(recipientAccountNo: String,
                                            amount: String,
                                            date: String,
                                            description: String): ViewState<BalanceData> {

        val mime= "application/json; charset=utf-8".toMediaTypeOrNull();
        val body: RequestBody = RequestBody.create(
            mime,
            JSONObject().apply {
                this.put("receipientAccountNo",recipientAccountNo)
                this.put("amount",amount.replace("$","").toDouble())
                this.put("date",date)
                this.put("description",description)
            }.toString())
        retrofit.create(ApiEndpoints::class.java).getTransferBalance(
            body
        ).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                it.errorBody()?.let {
                    return ViewState.Message(JSONObject(it.string()).getString("error"))
                }
            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }
}