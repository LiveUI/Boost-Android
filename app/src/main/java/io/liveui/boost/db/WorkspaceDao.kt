package io.liveui.boost.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.*
import io.reactivex.Completable


@Dao
interface WorkspaceDao {

    @Query("SELECT * FROM workspace")
    fun getWorkspaces(): LiveData<MutableList<Workspace>>

    @Query("SELECT * FROM workspace WHERE active = :active LIMIT 1")
    fun getActiveWorkspace(active: Int = 1): LiveData<Workspace?>

    @Query("SELECT jwt_token FROM workspace WHERE active = 1 LIMIT 1")
    fun getActiveJwtToken(): LiveData<String?>

    @Query("UPDATE workspace SET jwt_token = :token WHERE active = 1")
    fun updateActiveJwtToken(token: String?): Int

    @Query("UPDATE workspace SET active = 0 WHERE active = 1")
    fun setInactive()

    @Transaction
    fun setActive(workspace: Workspace): Completable {
        return Completable.fromCallable {
            setInactive()
            if (updateWorkspace(workspace.apply { active = 1 }) == 0) {
                insertWorkspace(workspace)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWorkspace(workspaces: Workspace): Long

    @Delete
    fun deleteWorkspace(workspace: Workspace)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWorkspace(workspace: Workspace): Int

}