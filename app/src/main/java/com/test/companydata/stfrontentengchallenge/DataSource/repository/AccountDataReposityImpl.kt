package com.test.companydata.stfrontentengchallenge.DataSource.repository


import android.content.Context
import com.test.companydata.ApiService.ApiEndpoints
import com.test.companydata.stfrontentengchallenge.DataSource.module.BalanceData
import com.test.companydata.stfrontentengchallenge.DataSource.module.PayeesData
import com.test.companydata.stfrontentengchallenge.DataSource.module.TransactionData
import com.test.companydata.stfrontentengchallenge.Domain.repository.AccountDataReposity
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import com.test.companydata.stfrontentengchallenge.R
import org.json.JSONObject
import retrofit2.Retrofit


class AccountDataReposityImpl(val context: Context, val retrofit: Retrofit): AccountDataReposity {
    override suspend fun getAccountBalances(): ViewState<BalanceData> {
        retrofit.create(ApiEndpoints::class.java).getUserBalances().let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                when(it){
                    is retrofit2.HttpException -> {
                        it.response()?.errorBody()?.let {
                            return ViewState.Message(JSONObject(it.string()).getString("message"))
                        }
                    }
                    else -> { }
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
                when(it){
                    is retrofit2.HttpException -> {
                        it.response()?.errorBody()?.let {
                            return ViewState.Message(JSONObject(it.string()).getString("message"))
                        }
                    }
                    else -> { }
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
                when(it){
                    is retrofit2.HttpException -> {
                        it.response()?.errorBody()?.let {
                            return ViewState.Message(JSONObject(it.string()).getString("message"))
                        }
                    }
                    else -> { }
                }

            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }


    override suspend fun getTransferBalance(recipientAccountNo: String,
                                            amount: String,
                                            date: String,
                                            description: String): ViewState<BalanceData> {
        retrofit.create(ApiEndpoints::class.java).getTransferBalance(
            recipientAccountNo,
            amount,
            date,
            description
        ).let {
            if(it.isSuccessful){
                it.body()?.let {
                    return ViewState.Content(it)
                }
            }else{
                when(it){
                    is retrofit2.HttpException -> {
                        it.response()?.errorBody()?.let {
                            return ViewState.Message(JSONObject(it.string()).getString("message"))
                        }
                    }
                    else -> { }
                }

            }
        }
        return ViewState.Message(context.resources.getString(R.string.some_error))
    }


}