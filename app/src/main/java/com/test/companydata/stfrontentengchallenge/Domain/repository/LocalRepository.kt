package com.test.companydata.stfrontentengchallenge.Domain.repository


interface LocalRepository {
    fun getUserAccountNo() : String
    fun getUserAccountHolder() : String
    fun getJwtTokent() : String?
}