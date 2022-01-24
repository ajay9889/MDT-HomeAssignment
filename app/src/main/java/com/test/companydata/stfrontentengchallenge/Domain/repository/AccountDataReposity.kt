package com.test.companydata.stfrontentengchallenge.Domain.repository

import com.test.companydata.stfrontentengchallenge.DataSource.module.*
import com.test.companydata.stfrontentengchallenge.Presentation.ViewModels.ViewState
import io.reactivex.Single

interface AccountDataReposity {
    suspend fun getAccountBalances() : ViewState<BalanceData>
    suspend fun getPayeesList() : ViewState<PayeesData>
    suspend fun getTransactionsList() : ViewState<TransactionData>
    suspend fun getTransferBalance(recipientAccountNo: String,
                                   amount: String,
                                   date: String,
                                   description: String) : ViewState<BalanceData>
}
