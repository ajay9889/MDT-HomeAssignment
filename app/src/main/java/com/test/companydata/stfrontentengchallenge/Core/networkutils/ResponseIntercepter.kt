package com.test.companydata.stfrontentengchallenge.Core.networkutils
import android.content.Context
import android.content.Intent
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils.ACTION_LOGOUT
import okhttp3.Interceptor
import okhttp3.Response
/**
 * This interecepter will work for checking the user token.. If expired then user will get logout
 * */
class ResponseIntercepter (val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        try {
            if (originalResponse.code == 401) {
                val responseBodyString: String = originalResponse.peekBody(Long.MAX_VALUE).string()
                (responseBodyString.contains("TokenExpiredError") || responseBodyString.contains("TokenExpiredError")).let {
                    if(it){
                        context.sendBroadcast(Intent(ACTION_LOGOUT))
                    }
                }
            }
        } catch (e: Exception){e.printStackTrace()}
        return originalResponse;
    }
}