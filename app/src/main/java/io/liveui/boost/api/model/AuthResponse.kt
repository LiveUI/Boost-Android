package io.liveui.boost.api.model

/**
 *  "token": "EA58FE6F-E2A0-404D-A252-1BBFB286A072",
 *  "last_used": "2018-01-10T18:38:35.762Z",
 *  "user_id": 1
 */
data class AuthResponse(val user_id: Int, val token: String, val last_used: String)