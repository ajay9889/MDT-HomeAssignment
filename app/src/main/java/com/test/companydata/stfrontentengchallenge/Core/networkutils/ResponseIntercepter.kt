package com.test.companydata.stfrontentengchallenge.Core.networkutils
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.test.companydata.stfrontentengchallenge.Core.Util.Utils.ACTION_LOGOUT
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.platform.android.AndroidLogHandler.close
import org.koin.java.KoinJavaComponent.inject

/**
 * This interecepter will work for checking the user token.. If expired then user will get logout
 * */
class ResponseIntercepter (val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        try {
            if (originalResponse.code == 401) {
                var isRequiredLogout = false;
                if (originalResponse.message.contains("TokenExpiredError") || originalResponse.message.contains("TokenExpiredError")) {
                    isRequiredLogout = true;
                } else {
                    val responses_: String = originalResponse.body?.string()?: "";
                    if (!TextUtils.isEmpty(responses_) && (responses_.contains("TokenExpiredError") || responses_.contains("TokenExpiredError"))) {
                        isRequiredLogout = true;
                    }
                }
                // get user logout when the TokenExpiredError
                if (isRequiredLogout) {
                    context.sendBroadcast(Intent(ACTION_LOGOUT))
                }
            }
        } catch (e: Exception){e.printStackTrace()}
        return originalResponse;
    }
}