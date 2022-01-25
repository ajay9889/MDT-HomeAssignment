package com.test.companydata.stfrontentengchallenge.Core.networkutils
import android.content.Context
import android.util.Log
import com.test.companydata.stfrontentengchallenge.DataSource.repository.LocalRepositoryImpl
import okhttp3.Interceptor
import okhttp3.Response
/**
 * This interecepter will work for adding the required header to commincate with endpoints
 * */
class HeaderIntercepter (val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =  chain.request().newBuilder()
            .header("Accept" ,"application/json" )
            .header("Content-Type" ,"application/json" )
            .header("Authorization" , LocalRepositoryImpl(context).getJwtTokent() ?: kotlin.run { ""})
            .build()
        return chain.proceed(request)
    }
}
