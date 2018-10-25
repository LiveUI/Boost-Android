package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostCheckService {

    @GET("ping")
    fun ping(): Observable<Message>

    @GET("teapot")
    fun teapot(): Observable<Message>

    @GET("info")
    fun getInfo(): Observable<ServerInfo>

//    @GET("error")
//    fun getErrors(): Observable<>

}