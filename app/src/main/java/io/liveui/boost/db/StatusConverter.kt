package io.liveui.boost.db

import android.arch.persistence.room.TypeConverter


class StatusConverter {

    @TypeConverter
    fun toStatus(status: Int): Workspace.Status {
        return when (status) {
            Workspace.Status.NEW.code -> Workspace.Status.NEW
            Workspace.Status.SERVER_VERIFIED.code -> Workspace.Status.SERVER_VERIFIED
            Workspace.Status.ACTIVATED.code -> Workspace.Status.ACTIVATED
            else -> throw IllegalArgumentException("Could not recognize status")
        }
    }

    @TypeConverter
    fun toInteger(status: Workspace.Status): Int {
        return status.code
    }
}