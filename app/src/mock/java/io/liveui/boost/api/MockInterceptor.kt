package io.liveui.boost.api

import io.liveui.boost.BuildConfig
import okhttp3.*


class MockInterceptor(val mockResponseResolver: MockResponseResolver) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        Thread.sleep(3000)

        val responseString: String = mockResponseResolver.getMockResponse(chain.request())

        val normalizeUrl = mockResponseResolver.normalizeUrl(BuildConfig.BASE_URL + "apps/android/cz.mangoweb.boost", "GET")

        return Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
    }


}