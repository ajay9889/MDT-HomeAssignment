package com.test.companydata.stfrontentengchallenge.Core.networkutils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class HeaderIntercepter (context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .header("Accept" ,"application/json" )
            .header("Content-Type" ,"application/x-www-form-urlencoded" )
            .header("Authorization" ,"" )
            .build()
    }
}
