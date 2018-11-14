package io.liveui.boost.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable


@Dao
interface WorkspaceDao {

    @Query("SELECT * FROM workspace")
    @WorkerThread
    fun getWorkspaces(): LiveData<MutableList<Workspace>>

    @Query("SELECT * FROM workspace WHERE active = :active LIMIT 1")
    @WorkerThread
    fun getActiveWorkspace(active: Int = 1): LiveData<Workspace?>

    @Query("SELECT jwt_token FROM workspace WHERE active = 1 LIMIT 1")
    @WorkerThread
    fun getActiveJwtToken(): LiveData<String?>

    @Query("UPDATE workspace SET jwt_token = :token WHERE active = 1")
    @WorkerThread
    fun updateActiveJwtToken(token: String?): Int

    @Query("UPDATE workspace SET active = 0 WHERE active = 1")
    @WorkerThread
    fun setInactive()

    @Transaction
    @WorkerThread
    fun setActive(workspace: Workspace) {
        setInactive()
        if (updateWorkspace(workspace.apply { active = 1 }) == 0) {
            insertWorkspace(workspace)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @WorkerThread
    fun insertWorkspace(workspaces: Workspace): Long

    @Delete
    @WorkerThread
    fun deleteWorkspace(workspace: Workspace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    @WorkerThread
    fun updateWorkspace(workspace: Workspace): Int

}