package com.test.companydata.stfrontentengchallenge.Core.networkutils
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.text.TextUtils
import android.util.Log
import com.test.companydata.stfrontentengchallenge.Core.Util.UserSecurePreferences
import com.test.companydata.stfrontentengchallenge.DataSource.repository.LocalRepositoryImpl
import com.test.companydata.stfrontentengchallenge.Presentation.Activity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                    // clear user credentials
                    UserSecurePreferences.getUserLogout(context)
                    CoroutineScope(Dispatchers.Main).launch {
                        context.startActivity(Intent(context, MainActivity::class.java).apply {
                            this.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            this.setFlags(FLAG_ACTIVITY_NEW_TASK)
                        })

                    }
                }
            }
        } catch (e: Exception){e.printStackTrace()}
        return originalResponse;
    }
}