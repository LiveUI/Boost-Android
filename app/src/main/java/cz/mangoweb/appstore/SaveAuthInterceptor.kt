package cz.mangoweb.appstore

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class SaveAuthInterceptor(val sharedPreferences: SharedPreferences): Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val response = chain?.proceed(chain.request())
        sharedPreferences.edit().putString("jwtToken", response?.headers()?.get("Authorization")).apply()
        return chain!!.proceed(chain.request())
    }
}