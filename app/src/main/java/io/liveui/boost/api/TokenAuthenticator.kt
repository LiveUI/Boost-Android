package io.liveui.boost.api

import android.content.SharedPreferences
import io.liveui.boost.api.model.RefreshTokenRequest
import io.liveui.boost.api.service.BoostAuthService
import io.liveui.boost.db.Workspace
import okhttp3.*
import timber.log.Timber
import java.lang.Exception

class TokenAuthenticator constructor(private val authService: BoostAuthService,
                                     private val workspace: Workspace,
                                     private val sharedPreferences: SharedPreferences) : Authenticator {

    private var retryCount = 0

    override fun authenticate(route: Route?, response: Response?): Request? {
        val builder = response?.request()?.newBuilder()
        Timber.e("Auth start")
        try {
            val tokenResponse = authService.refreshToken(RefreshTokenRequest(workspace.permToken)).toFuture().get()
            val token = sharedPreferences.getString("jwtToken", null)
            if (token != null) {
                retryCount = 0
                builder?.removeHeader("Authorization")
                builder?.addHeader("Authorization", token)
            }
            return builder?.build()
        } catch (e: Exception) {
            Timber.d(e, "Failed to refresh token")

            retryCount++

            Timber.e("Auth retry attempt $retryCount")
            return if (retryCount > 3) {
                retryCount = 0
                null
            } else {
                builder?.build()
            }
        }

    }
}