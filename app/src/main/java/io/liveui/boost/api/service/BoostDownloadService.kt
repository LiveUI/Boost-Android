package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BoostDownloadService {

    @GET("apps/{id}/auth")
    fun getDownloadToken(@Path("id") id: String): Observable<AppTokenResponse>

    @GET("apps/file")
    fun downloadApp(@Query("token", encoded = true) token: String): Observable<Response<ResponseBody>>

    @GET("apps/plist")
    fun getPropertyListFile(@Query("download", encoded = true) token: String): Observable<Response<ResponseBody>>

}