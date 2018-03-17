package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.AuthRequest
import io.liveui.boost.api.model.AuthResponse
import io.liveui.boost.api.model.RefreshTokenRequest
import io.liveui.boost.api.model.RefreshTokenResponse
import io.liveui.boost.api.service.BoostAuthService
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