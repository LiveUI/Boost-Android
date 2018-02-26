package cz.mangoweb.appstore.api.model

/**
 * {
 * "last_used": "2018-01-10T18:38:35.762Z",
 * "user_id": 1
 * }
 */
data class RefreshTokenResponse(val last_used: Long, val user_id: String)