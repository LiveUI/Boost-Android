package io.liveui.boost.api.model

/**
 * {
"initials": "AT",
"id": "79EF2805-D627-4354-916B-AD8A037DF11B",
"admin": true,
"name": "Admin team",
"identifier": "admin-team",
"color": "5C94E1"
 * }
 */
data class Team(val id: String, val name: String, val identifier: String, val initials: String, val admin: Boolean, val color: String)