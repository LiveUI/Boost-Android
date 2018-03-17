package io.liveui.boost.api

import android.content.SharedPreferences
import io.liveui.boost.api.model.RefreshTokenRequest
import io.liveui.boost.api.service.BoostAuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenitcator constructor(private val authService: BoostAuthService,
                                     private val sharedPreferences: SharedPreferences): Authenticator {

    override fun authenticate(route: Route?, response: Response?): Request? {
        authService.refreshToken(RefreshTokenRequest(sharedPreferences.getString("token", null))).blockingSubscribe()
        return response?.request()
    }
}