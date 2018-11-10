package io.liveui.boost.api.model

/**
 * {
 * "name": "Booster!",
 * "url": "http://localhost:8080",
 * "icons": [
 * {
 * "url": "http://localhost:8080/server/image/16",
 * "size": 16
 * },
 * {
 * "url": "http://localhost:8080/server/image/64",
 * "size": 64
 * },
 * {
 * "url": "http://localhost:8080/server/image/128",
 * "size": 128
 * },
 * {
 * "url": "http://localhost:8080/server/image/192",
 * "size": 192
 * },
 * {
 * "url": "http://localhost:8080/server/image/256",
 * "size": 256
 * },
 * {
 * "url": "http://localhost:8080/server/image/512",
 * "size": 512
 * }
 * ]
 * }
 */
data class ServerInfo(val name: String, val url: String, val icons: List<Icon>?)

data class Icon(val url: String, val size: Int)