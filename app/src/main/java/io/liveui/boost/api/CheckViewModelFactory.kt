package io.liveui.boost.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.login.LoginViewModel
import io.liveui.boost.ui.workspace.WorkspaceViewModel


class CheckViewModelFactory constructor(private val checkUseCase: BoostCheckUseCase, private val workspaceDao: WorkspaceDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceViewModel::class.java)) {
            return WorkspaceViewModel(checkUseCase,workspaceDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}