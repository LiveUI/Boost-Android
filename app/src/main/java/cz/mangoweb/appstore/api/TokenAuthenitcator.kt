package cz.mangoweb.appstore.api

import android.content.SharedPreferences
import cz.mangoweb.appstore.api.model.RefreshTokenRequest
import cz.mangoweb.appstore.api.service.BoostApiService
import cz.mangoweb.appstore.api.service.BoostAuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class TokenAuthenitcator constructor(private val authService: BoostAuthService,
                                     private val sharedPreferences: SharedPreferences): Authenticator {

    override fun authenticate(route: Route?, response: Response?): Request? {
        val blockingFirst = authService.refreshToken(RefreshTokenRequest(sharedPreferences.getString("token", null))).blockingFirst()
        Timber.d(blockingFirst.toString())
        return response?.request()
    }
}