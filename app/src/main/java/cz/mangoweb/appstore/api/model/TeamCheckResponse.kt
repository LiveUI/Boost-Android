package cz.mangoweb.appstore.api.model

/**
 * {
 * "code": "ok"
 * "error": "Identifier is available",
 * "identifier": "my-new-team"
 * }
 */
data class TeamCheckResponse(val code: String, val error: String, val identifier: String)