package io.liveui.boost.api.model


/**
 * {
"expires": 543969500.303784,
"id": "62D26F36-4E50-4D5C-921D-A0B30672BC83",
"token": "F8CAB8E4-F507-46AC-9F15-B2C369C23126",
"user": {
"password": "admin",
"id": "630C97E6-AC56-4213-882B-3BEBAE50BF6D",
"lastname": "Admin",
"registered": 541114427,
"firstname": "Super",
"email": "admin@liveui.io",
"su": true,
"disabled": false
}
}
 */
data class AuthResponse(val id: String,
                        val token: String,
                        val expires: String,
                        val user: User)