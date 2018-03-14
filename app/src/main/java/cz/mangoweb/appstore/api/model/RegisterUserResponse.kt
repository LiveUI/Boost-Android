package cz.mangoweb.appstore.api.model

data class RegisterUserResponse(val firstname: String, val lastname: String, val email: String, val disabled: Boolean, val su: Boolean, val registered: Long)