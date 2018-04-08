package io.liveui.boost.api

import okhttp3.*


class MockInterceptor(val mockResponseResolver: MockResponseResolver) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        Thread.sleep(2000)

        val responseString: String = mockResponseResolver.getMockResponse(chain.request())

        return Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTk4NTk0ODIuOTgwNzgzOSwidXNlcklkIjoiNjMwQzk3RTYtQUM1Ni00MjEzLTg4MkItM0JFQkFFNTBCRjZEIn0.aPMfWZnHJSHa_rDxY-u9x-Vs-amQvxeVL8zGrPhwNGU")
                .addHeader("Content-Disposition", "attachment; filename=test-app.apk")
                .build()
    }

}