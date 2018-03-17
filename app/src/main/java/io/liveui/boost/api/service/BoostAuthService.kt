package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostAuthService {

    @POST("auth")
    fun auth(@Body auth: AuthRequest): Observable<AuthResponse>

    @POST("token")
    fun refreshToken(@Body token: RefreshTokenRequest): Observable<RefreshTokenResponse>

}