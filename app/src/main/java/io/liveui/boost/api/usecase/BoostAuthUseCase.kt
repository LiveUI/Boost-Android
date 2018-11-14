package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.*
import io.liveui.boost.api.service.BoostAuthService
import io.liveui.boost.util.UrlProvider
import io.liveui.boost.util.ext.path
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BoostAuthUseCase @Inject constructor(private val authService: BoostAuthService,
                                           private val urlProvider: UrlProvider) {

    fun auth(url: String, request: AuthRequest): Observable<AuthResponse> {
        return authService.auth(url.path("auth"), request).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun registerUser(url: String, user: RegisterUser): Observable<User> {
        return authService.registerUser(url.path("users"), user).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun refreshToken(token: RefreshTokenRequest): Observable<RefreshTokenResponse> {
        return authService.refreshToken(urlProvider.getUrl().path("token"), token)
    }
}