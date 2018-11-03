package io.liveui.boost.common

import androidx.lifecycle.MutableLiveData
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor(val workspaceDao: WorkspaceDao) {

    var workspace: Workspace = Workspace(url = "")
    var workspaceChanged: MutableLiveData<Workspace> = MutableLiveData() //TODO reload screens on workspace change

    init {
        workspaceDao.getWorkspaces().observeForever { workspace ->
            workspace?.find { it.active == 1 }?.let {
                this.workspace = it
                workspaceChanged.postValue(it)
            }
        }
    }

}