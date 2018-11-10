package io.liveui.boost.api

import io.liveui.boost.BuildConfig
import io.liveui.boost.util.UrlProvider
import javax.inject.Singleton

@Singleton
class BoostUrlProvider : UrlProvider {

    var baseUrl = BuildConfig.URL[0]

    override fun getUrl(): String {
        return baseUrl
    }

    override fun getDefaultUrl(): String = BuildConfig.URL[0]

}