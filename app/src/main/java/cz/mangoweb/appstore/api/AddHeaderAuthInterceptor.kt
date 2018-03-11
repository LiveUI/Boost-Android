package cz.mangoweb.appstore.api

import okhttp3.Interceptor
import okhttp3.Response

class AddHeaderAuthInterceptor(val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain!!.request()!!.newBuilder()?.addHeader("Authorization", token)!!.build()
        return chain.proceed(request)
    }
}