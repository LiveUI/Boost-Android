package io.liveui.boost.api.model

interface IApp {
    fun getAppId(): String
    fun getAppIdentifier(): String
    fun getAppVersion(): String
    fun getAppPlatform(): String
    fun getAppName(): String
    fun getAppBuild(): String
}