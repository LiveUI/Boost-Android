package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostAuthService {

    @POST
    fun auth(@Url url: String, @Body auth: AuthRequest): Observable<AuthResponse>

    @POST
    fun registerUser(@Url url: String, @Body user: RegisterUser): Observable<User>

    @POST
    fun refreshToken(@Url url: String, @Body token: RefreshTokenRequest): Observable<RefreshTokenResponse>

}