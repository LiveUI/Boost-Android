package io.liveui.boost.api.model

/**
 * {
 * "id": "38660437-6000-4AC7-BEE8-EBFB7079BA9A",
 * "user": {
 *      "id": "D34B4093-9F3A-4E03-97DF-69686EE030C9",
 *      "lastname": "Admin",
 *      "registered": "2018-10-08T11:10:57Z",
 *      "firstname": "Super",
 *      "username": "admin",
 *      "email": "core@liveui.io",
 *      "su": true,
 *      "disabled": false
 * },
 * "expires": "2018-11-08T12:37:08Z"
 * }
 */
data class RefreshTokenResponse(val id: String, val expires: String, val user: User)