package io.liveui.boost.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Dao
interface WorkspaceDao {

    @Query("SELECT * FROM workspace")
    fun getWorkspaces(): LiveData<List<Workspace>>

    @Query("SELECT * FROM workspace WHERE active = :active LIMIT 1")
    fun getActiveWorkspace(active: Int = 1): LiveData<Workspace>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertWorkspace(workspaces: Workspace)

    @Delete
    fun deleteWorkspace(workspace: Workspace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWorkspace(workspace: Workspace)

}