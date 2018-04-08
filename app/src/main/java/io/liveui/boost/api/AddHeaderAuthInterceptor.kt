package io.liveui.boost.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddHeaderAuthInterceptor(val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val builder: Request.Builder? = chain!!.request()!!.newBuilder()
        if (token != null) {
            builder?.addHeader("Authorization", token)
        }
        return chain.proceed(builder!!.build())
    }
}