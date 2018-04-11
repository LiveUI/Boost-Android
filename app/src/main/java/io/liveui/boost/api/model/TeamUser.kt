package io.liveui.boost.api.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * {
"id": "630C97E6-AC56-4213-882B-3BEBAE50BF6D",
"lastname": "Admin",
"registered": 541114427,
"su": true,
"email": "admin@liveui.io",
"firstname": "Super",
"disabled": false
}
 */
@Entity(tableName = "users")
data class TeamUser(@PrimaryKey @ColumnInfo(name = "id") val id: String,
                    @ColumnInfo(name = "firstname") val firstname: String,
                    @ColumnInfo(name = "lastname") val lastname: String,
                    @ColumnInfo(name = "registered") val registered: String,
                    @ColumnInfo(name = "email") val email: String,
                    @ColumnInfo(name = "su") val su: Boolean,
                    @ColumnInfo(name = "disabled") val disabled: Boolean,
                    @ColumnInfo(name = "link_id") var linkId: Long = 0) {

    fun getFullName(): String {
        return "$firstname $lastname"
    }
}