package io.liveui.boost.api

import io.liveui.boost.util.AuthHeaderProvider
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AddHeaderAuthInterceptor @Inject constructor(private val authHeaderProvider: AuthHeaderProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val builder: Request.Builder? = chain!!.request()!!.newBuilder()
        authHeaderProvider.applyHeader(builder)
        return chain.proceed(builder!!.build())
    }
}