package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BoostDownloadService {

    @GET("apps/{id}/auth")
    fun getDownloadToken(@Path("id") id: String): Observable<AppTokenResponse>

    @GET("apps/{id}/file?download={token}")
    fun downloadApp(@Path("id") id: String, @Path("token") token: String): Observable<Response<ResponseBody>>

    @GET("apps/plist?download={token}")
    fun getPropertyListFile(@Path("token") token: String): Observable<Response<ResponseBody>>

}