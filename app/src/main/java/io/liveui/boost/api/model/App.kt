package io.liveui.boost.api.model

/**
 * {
 * "id": "65BDE562-52B4-45B3-81F7-D02D4688B5DA", -
 * "version": "0.0", -
 * "size": 25036179,
 * "cluster_id": "44056713-8242-4C6C-994C-1AAB7F0455EA",
 * "platform": "android", -
 * "identifier": "com.kbc.mobilebanking.saj", -
 * "size_total": 25046423,
 * "created": "2018-10-20T12:07:16Z",
 * "min_sdk": "",
 * "icon": true,
 * "team_id": "35A9C43D-094D-40E8-8ED1-CAC82426D18B",
 * "name": "mobilebanking",
 * "build": "0"
 * }
 */
data class App(val build: String,
               val id: String,
               val platform: String,
               val team_id: String,
               val version: String,
               val identifier: String,
               val created: String,
               val name: String,
               val min_sdk: String?,
               val icon: Boolean,
               val size_total: Long): IApp {

    override fun getAppId(): String {
       return id
    }

    override fun getAppIdentifier(): String {
        return identifier
    }
}