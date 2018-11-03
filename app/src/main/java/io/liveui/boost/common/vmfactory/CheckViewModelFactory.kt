package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import javax.inject.Provider


class CheckViewModelFactory constructor(val workspaceAddViewModelProvider: Provider<WorkspaceAddViewModel>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceAddViewModel::class.java)) {
            return workspaceAddViewModelProvider.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}