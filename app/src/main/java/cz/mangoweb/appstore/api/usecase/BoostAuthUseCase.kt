package cz.mangoweb.appstore.api.usecase

import cz.mangoweb.appstore.api.model.AuthRequest
import cz.mangoweb.appstore.api.model.AuthResponse
import cz.mangoweb.appstore.api.model.RefreshTokenRequest
import cz.mangoweb.appstore.api.model.RefreshTokenResponse
import cz.mangoweb.appstore.api.service.BoostAuthService
import io.reactivex.Observable
import javax.inject.Inject

class BoostAuthUseCase @Inject constructor(private val authService: BoostAuthService) {

    fun auth(request: AuthRequest): Observable<AuthResponse> {
        return authService.auth(request)
    }

    fun refreshToken(token: RefreshTokenRequest): Observable<RefreshTokenResponse> {
        return authService.refreshToken(token)
    }
}