package cz.mangoweb.appstore.api

import okhttp3.*

class MockClient: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val responseString: String = "{\"token\": \"EA58FE6F-E2A0-404D-A252-1BBFB286A072\",\"last_used\": \"2018-01-10T18:38:35.762Z\", \"user_id\": 1}" //TODO load json from assets

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