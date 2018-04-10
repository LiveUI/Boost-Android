package io.liveui.boost.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.liveui.boost.api.model.TeamUser


@Dao
interface WorkspaceDao {

    @Query("SELECT * FROM workspace")
    fun getWorkspaces(): List<Workspace>

    @Query("SELECT * FROM workspace WHERE uid = :uid LIMIT 1")
    fun getActiveWorkspace(uid: Long): LiveData<Workspace>

    @Insert
    fun insertWorkspace(workspaces: Workspace)

    @Delete
    fun deleteWorkspace(workspace: Workspace)

    @Update
    fun updateWorkspace(workspace: Workspace)

    @Query("SELECT * FROM users WHERE uid = :uid LIMIT 1")
    fun getUser(uid: Long): LiveData<TeamUser>

    @Insert
    fun insertUser(user: TeamUser)

    @Delete
    fun deleteUser(user: TeamUser)

    @Update
    fun updateUser(user: TeamUser)
}