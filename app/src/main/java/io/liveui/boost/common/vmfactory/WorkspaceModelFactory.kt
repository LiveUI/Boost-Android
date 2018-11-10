package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import io.liveui.boost.ui.workspace.all.WorkspaceListViewModel
import javax.inject.Provider


class WorkspaceModelFactory constructor(val workspaceViewModelProvider: Provider<WorkspaceListViewModel>,
                                        val workspaceAddViewModelProvider: Provider<WorkspaceAddViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceListViewModel::class.java)) {
            return workspaceViewModelProvider.get() as T
        } else if (modelClass.isAssignableFrom(WorkspaceAddViewModel::class.java)) {
            return workspaceAddViewModelProvider.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}