package io.liveui.boost.util

interface UrlProvider {

    fun getUrl(): String

    fun setUrl(url: String)

    fun getDefaultUrl(): String
}