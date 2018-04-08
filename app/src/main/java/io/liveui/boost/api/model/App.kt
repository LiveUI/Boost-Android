package io.liveui.boost.api.model

/**
 * {
 * "build": "123",
 * "basic": false,
 * "id": 3,
 * "platform": 2,
 * "team_id": 1,
 * "version": "1.2.3",
 * "identifier": "com.boost.super-app1",
 * "created": 535044877,
 * "name": "Super app3",
 * "modified": 535044877
 * }
 */
data class App(val build: String,
               val basic: Boolean,
               val id: String,
               val platform: String,
               val team_id: String,
               val version: String,
               val identifier: String,
               val created: String,
               val name: String,
               val modified: String)