package io.liveui.boost.api.model

data class AuthResponse(val id: String,
                        val token: String,
                        val expires: Double,
                        val user: TeamUser)