package cz.mangoweb.appstore.api.model

/**
 * {
 * "username": "admin@liveui.io"
 * "password": "s3cretPassw0rd"
 * }
 */
data class AuthRequest(val username: String, val password: String)