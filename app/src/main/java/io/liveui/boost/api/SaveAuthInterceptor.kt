package io.liveui.boost.api

import io.liveui.boost.util.AuthHeaderProvider
import io.liveui.boost.util.HEADER_AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SaveAuthInterceptor @Inject constructor(private val authHeaderProvider: AuthHeaderProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val response = chain?.proceed(chain.request())
        response?.headers()?.get(HEADER_AUTHORIZATION)?.let {
            authHeaderProvider.saveHeader(it)
        }
        return chain!!.proceed(chain.request())
    }
}