package cz.mangoweb.appstore.api.service

import cz.mangoweb.appstore.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostAuthService {

    @POST("auth")
    fun auth(@Body auth: AuthRequest): Observable<AuthResponse>

    @POST("token")
    fun refreshToken(@Body token: RefreshTokenRequest): Observable<RefreshTokenResponse>

}