package io.liveui.boost.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.util.LifecycleViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(val userSession: UserSession, val workspaceDao: WorkspaceDao) : LifecycleViewModel() {

    val workspaceStatus: LiveData<Workspace.Status> = Transformations.map(userSession.activeWorkspace) {
        it?.status ?: Workspace.Status.NEW
    }


}