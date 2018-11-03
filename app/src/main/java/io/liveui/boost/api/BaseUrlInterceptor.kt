package io.liveui.boost.api

import okhttp3.Interceptor
import okhttp3.Response
import io.liveui.boost.db.Workspace
import okhttp3.HttpUrl
import java.net.MalformedURLException
import java.net.URL

//TODO change to url provider
class BaseUrlInterceptor(val workspace: Workspace) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        val newUrl = request.url().newBuilder()
        if(workspace.url.isNotEmpty()) {
            try {
                val url = URL(workspace.url)
                newUrl.host(url.host)
                newUrl.scheme(url.protocol)
                val port = url.port
                if (port != -1) {
                    newUrl.port(port)
                } else {
                    newUrl.port(HttpUrl.defaultPort(url.protocol))
                }
            } catch (e: MalformedURLException) {
                val urlPart = workspace.url.split(":")
                newUrl.host(urlPart[0])
                if (urlPart.size > 1) {
                    newUrl.port(urlPart[1].toInt())
                }
            }
        }
        request = request.newBuilder()
                .url(newUrl.build())
                .build()
        return chain.proceed(request)
    }

}