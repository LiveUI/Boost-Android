package cz.mangoweb.appstore.api.service

import cz.mangoweb.appstore.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostDownloadService {

    @GET("apps/{id}/auth")
    fun getDownloadToken(@Path("id") id: Int): Observable<AppTokenResponse>

    @GET("apps/{id}/file?download={token}")
    fun downloadApp(@Path("id") id: Int, @Path("token") token: String)

}