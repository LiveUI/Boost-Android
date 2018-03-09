package cz.mangoweb.appstore.api

import okhttp3.*


class MockClient(val mockResponseResolver: MockResponseResolver) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        Thread.sleep(3000)

        val responseString: String = mockResponseResolver.getMockResponse(chain.request())

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