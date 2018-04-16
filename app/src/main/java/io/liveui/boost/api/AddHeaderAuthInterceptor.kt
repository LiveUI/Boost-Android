package io.liveui.boost.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddHeaderAuthInterceptor(val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val builder: Request.Builder? = chain!!.request()!!.newBuilder()
        val token = sharedPreferences.getString("jwtToken", null)
        if (token != null) {
            builder?.addHeader("Authorization", token)
        }
        return chain.proceed(builder!!.build())
    }
}