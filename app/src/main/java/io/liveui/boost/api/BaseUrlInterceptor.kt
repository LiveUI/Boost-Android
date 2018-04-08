package io.liveui.boost.api

import okhttp3.Interceptor
import okhttp3.Response
import io.liveui.boost.common.model.Workspace
import java.net.MalformedURLException
import java.net.URL


class BaseUrlInterceptor(val workspace: Workspace) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        val newUrl = request.url().newBuilder()
        try {
            val url = URL(workspace.url)
            newUrl.host(url.host)
            newUrl.scheme(url.protocol)
            newUrl.port(url.port)
        } catch (e: MalformedURLException) {
            val urlPart = workspace.url!!.split(":")
            newUrl.host(urlPart[0])
            if(urlPart.size > 1) {
                newUrl.port(urlPart[1].toInt())
            }
        }
        request = request.newBuilder()
                .url(newUrl.build())
                .build()
        return chain.proceed(request)
    }

}