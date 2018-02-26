package cz.mangoweb.appstore.api.model

/**
 * {
 * "name": "Admin team",
 * "identifier": "admin-team"
 * }
 */
data class CreateTeamRequest(val name: String, val identifier: String)