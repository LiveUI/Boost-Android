package io.liveui.boost.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WorkspaceDao {

    @Query("SELECT * FROM workspace")
    fun getWorkspaces(): LiveData<MutableList<Workspace>>

    @Query("SELECT * FROM workspace WHERE active = :active LIMIT 1")
    fun getActiveWorkspace(active: Int = 1): LiveData<Workspace>

    @Query("UPDATE workspace SET active = 0 WHERE active = 1")
    fun setInactive()

    @Transaction
    fun setActive(workspace: Workspace) {
        setInactive()
        if(updateWorkspace(workspace.apply { active = 1 }) == 0) {
            insertWorkspace(workspace)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWorkspace(workspaces: Workspace): Long

    @Delete
    fun deleteWorkspace(workspace: Workspace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWorkspace(workspace: Workspace): Int

}