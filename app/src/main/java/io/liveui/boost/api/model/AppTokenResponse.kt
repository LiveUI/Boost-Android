package io.liveui.boost.api.model

/**
 * {
 * "file": "http://localhost:8080/apps/file?token=7F497708-5593-4DC1-9110-BB19B5EB131E",
 * "ios": "itms-services://?action=download-manifest&url=http://localhost:8080/apps/plist?token=7F497708-5593-4DC1-9110-BB19B5EB131E",
 * "app_id": "845040EE-2DBF-41BF-BC20-98136805AE00",
 * "token": "7F497708-5593-4DC1-9110-BB19B5EB131E",
 * "plist": "http://localhost:8080/apps/plist?token=7F497708-5593-4DC1-9110-BB19B5EB131E"
 * }
 */
data class AppTokenResponse(val token: String,
                            val file: String,
                            val ios: String?,
                            val app_id: String,
                            val plist: String?)