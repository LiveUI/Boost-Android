package io.liveui.boost.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import io.liveui.boost.api.model.TeamUser;

@Database(entities = {Workspace.class, TeamUser.class}, version = 2)
@TypeConverters(StatusConverter.class)
public abstract class BoostDatabase extends RoomDatabase {
    public abstract WorkspaceDao workspaceDao();
}