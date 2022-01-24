package com.test.companydata.stfrontentengchallenge.Domain.model

import com.test.companydata.stfrontentengchallenge.DataSource.module.BalanceData
import com.test.companydata.stfrontentengchallenge.DataSource.module.DataX
import com.test.companydata.stfrontentengchallenge.DataSource.module.TransactionData

sealed class DashaboardItems{
    data class Ballance(val mBalanceData: BalanceData) : DashaboardItems()
    data class Transactions(val mData: DataX) : DashaboardItems()
}

fun getFinalist(mBalanceData: BalanceData , mTransactionData: TransactionData): List<DashaboardItems> =
    ArrayList<DashaboardItems>().also {dashaboardItems->
        dashaboardItems.add(DashaboardItems.Ballance(mBalanceData))
        mTransactionData.let {
            it.data?.map {
                dashaboardItems.add(DashaboardItems.Transactions(it))
            }
        }
}