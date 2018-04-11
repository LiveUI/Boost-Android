package io.liveui.boost.common.vmfactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.splash.SplashViewModel


class SplashViewModelFactory constructor(private val session: UserSession,
                                         private val workspaceDao: WorkspaceDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(session, workspaceDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}