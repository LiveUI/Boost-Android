package cz.mangoweb.appstore.api.model

/**
 * {
 * "username": "admin@liveui.io"
 * "password": "s3cretPassw0rd"
 * }
 */
data class AuthRequest(val email: String, val password: String)