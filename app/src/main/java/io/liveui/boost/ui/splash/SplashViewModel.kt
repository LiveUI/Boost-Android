package io.liveui.boost.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao

class SplashViewModel(val userSession: UserSession, val workspaceDao: WorkspaceDao) : ViewModel() {

    fun loadData(): LiveData<Workspace> {
        return workspaceDao.getActiveWorkspace()
    }
}