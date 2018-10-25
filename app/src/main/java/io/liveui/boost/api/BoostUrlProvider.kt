package io.liveui.boost.api

import io.liveui.boost.BuildConfig
import io.liveui.boost.util.UrlProvider

class BoostUrlProvider: UrlProvider {

    override fun getUrl(): String {
        return BuildConfig.URL[0]
    }

}