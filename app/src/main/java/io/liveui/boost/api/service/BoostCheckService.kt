package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostCheckService {

    @GET
    fun ping(@Url url: String): Observable<Message>

    @GET("teapot")
    fun teapot(): Observable<Message>

    @GET
    fun getInfo(@Url url: String): Observable<ServerInfo>

}