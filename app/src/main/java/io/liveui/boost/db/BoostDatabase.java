package io.liveui.boost.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import io.liveui.boost.api.model.User;

@Database(entities = {Workspace.class, User.class}, version = 1)
@TypeConverters(StatusConverter.class)
public abstract class BoostDatabase extends RoomDatabase {
    public abstract WorkspaceDao workspaceDao();
}