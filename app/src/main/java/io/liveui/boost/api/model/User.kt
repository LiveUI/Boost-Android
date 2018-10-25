package io.liveui.boost.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * {
 * "id": "630C97E6-AC56-4213-882B-3BEBAE50BF6D",
 * "lastname": "Admin",
 * "registered": 541114427,
 * "su": true,
 * "email": "admin@liveui.io",
 * "firstname": "Super",
 * "disabled": false
 * }
 */
@Entity(tableName = "users")
data class User(@PrimaryKey @ColumnInfo(name = "id") val id: String,
                @ColumnInfo(name = "username") val username: String,
                @ColumnInfo(name = "firstname") val firstname: String,
                @ColumnInfo(name = "lastname") val lastname: String,
                @ColumnInfo(name = "registered") val registered: String,
                @ColumnInfo(name = "avatar") val avatar: String?,
                @ColumnInfo(name = "email") val email: String?,
                @ColumnInfo(name = "su") val su: Boolean,
                @ColumnInfo(name = "disabled") val disabled: Boolean) {

    fun getFullName(): String {
        return "$firstname $lastname"
    }
}