package io.liveui.boost.ui.workspace.all

import androidx.lifecycle.LiveData
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.util.LifecycleViewModel
import javax.inject.Inject

class WorkspaceListViewModel @Inject constructor(val workspaceDao: WorkspaceDao) : LifecycleViewModel() {

    val workspace: LiveData<MutableList<Workspace>>  =  workspaceDao.getWorkspaces()

}