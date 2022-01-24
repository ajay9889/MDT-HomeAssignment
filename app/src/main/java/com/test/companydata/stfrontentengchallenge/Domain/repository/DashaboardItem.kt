package com.test.companydata.stfrontentengchallenge.Domain.repository

sealed class DashaboardItem(){
    data class Header(val title: String) : DashaboardItem()
    data class Transa(val title: String) : DashaboardItem()
}
