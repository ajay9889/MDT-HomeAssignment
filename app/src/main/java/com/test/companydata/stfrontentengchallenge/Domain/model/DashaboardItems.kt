package com.test.companydata.stfrontentengchallenge.Domain.model

import com.test.companydata.stfrontentengchallenge.DataSource.module.BalanceData
import com.test.companydata.stfrontentengchallenge.DataSource.module.DataX
import com.test.companydata.stfrontentengchallenge.DataSource.module.TransactionData

sealed class DashaboardItems{
    data class Title(val headerTitle: String) : DashaboardItems()
    data class Ballance(val mBalanceData: BalanceData) : DashaboardItems()
    data class Transactions(val trnsItem : Pair<String, List<DataX>>) : DashaboardItems()
}