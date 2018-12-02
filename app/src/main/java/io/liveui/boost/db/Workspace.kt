package io.liveui.boost.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.liveui.boost.api.model.User
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "workspace")
data class Workspace(@ColumnInfo(name = "name") var name: String? = null,
                     @PrimaryKey @ColumnInfo(name = "url") var url: String = "",
                     @ColumnInfo(name = "perm_token") var permToken: String? = null,
                     @ColumnInfo(name = "jwt_token") var jwtToken: String? = null,
                     @ColumnInfo(name = "active") var active: Int = 1,
                     @ColumnInfo(name = "status") var status: Status = Status.NEW): Parcelable {

    @Embedded
    var user: User? = null

    enum class Status(val code: Int) {
        NEW(0),
        SERVER_VERIFIED(1),
        ACTIVATED(2)
    }

}