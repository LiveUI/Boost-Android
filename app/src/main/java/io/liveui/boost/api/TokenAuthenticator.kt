package io.liveui.boost.api

import io.liveui.boost.api.model.RefreshTokenRequest
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.common.UserSession
import io.liveui.boost.util.AuthHeaderProvider
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(private val authUseCase: BoostAuthUseCase,
                                     private val userSession: UserSession,
                                     private val authHeaderProvider: AuthHeaderProvider) : Authenticator {

    private var retryCount = 0

    override fun authenticate(route: Route?, response: Response?): Request? {
        val builder = response?.request()?.newBuilder()
        try {
            Timber.e("Authentication Start")
            userSession.setTokenInfo(authUseCase.refreshToken(RefreshTokenRequest(userSession.permanentToken)).toFuture().get())
            authHeaderProvider.applyHeader(builder)
            Timber.e("Authentication Finish")
            return builder?.build()
        } catch (e: Exception) {
            Timber.d(e, "Authentication - Failed to refresh token")
            retryCount++
            Timber.e("Authentication - Auth retry attempt $retryCount")
            return if (retryCount > 3) {
                Timber.d(e, "Authentication - Failed - Unable to authenticate after third attempt")
                retryCount = 0
                null
            } else {
                builder?.build()
            }
        }

    }
}