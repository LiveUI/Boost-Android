package io.liveui.boost.api.model

/**
 * {
 * "latest_app_build": "0", -
 * "platform": "android", -
 * "latest_app_id": "65BDE562-52B4-45B3-81F7-D02D4688B5DA", -
 * "identifier": "com.kbc.mobilebanking.saj", -
 * "latest_app_version": "0.0", -
 * "latest_app_name": "mobilebanking", -
 * "latest_app_icon": true,
 * "latest_app_added": "2018-10-22T16:33:54Z",
 * "build_count": 2
 * }
 */
data class AppOverview(val latest_app_name: String,
                       val build_count: Int,
                       val latest_app_build: String,
                       val latest_app_version: String,
                       val latest_app_added: String?,
                       val identifier: String,
                       val platform: String,
                       val latest_app_icon: Boolean,
                       val latest_app_id: String) : IApp {

    override fun getAppVersion() = latest_app_version
    override fun getAppPlatform() = platform
    override fun getAppId() = latest_app_id
    override fun getAppIdentifier() = identifier
    override fun getAppName() = latest_app_name
    override fun getAppBuild() = latest_app_build
    override fun hasIcon(): Boolean = latest_app_icon

}