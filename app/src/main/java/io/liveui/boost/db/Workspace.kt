package io.liveui.boost.db

import android.arch.persistence.room.*
import android.content.SharedPreferences
import android.text.TextUtils
import android.webkit.URLUtil
import androidx.content.edit
import io.liveui.boost.PREF_WORKSPACE
import io.liveui.boost.util.ext.fromJson
import io.liveui.boost.util.ext.toJson

@Entity(tableName = "workspace")
data class Workspace(@ColumnInfo(name = "name") var name: String? = null,
                     @ColumnInfo(name = "url") var url: String? = null,
                     @ColumnInfo(name = "perm_token") var permToken: String? = null,
                     @ColumnInfo(name = "status") var status: Status = Status.NEW) {

    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0

    enum class Status(val code: Int) {
        NEW(0),
        SERVER_VERIFIED(1),
        ACTIVATED(2)
    }

}