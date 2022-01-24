package com.test.companydata.Core.networkutils
import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class CacheInterceptor (private val context: Context) : Interceptor {
    private val maxStale = 60 * 60 * 24 * 19
    @Throws(IOException::class)
    override
    fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkConnectivity.isNetworkConnected(context)) {
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
        }
        return chain.proceed(request)
    }

}

class OnlineCacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override
    fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val maxAge = 0 // read from cache for 0 second even if there is internet connection
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=$maxAge")
                .removeHeader("Pragma")
                .build()
    }
}
