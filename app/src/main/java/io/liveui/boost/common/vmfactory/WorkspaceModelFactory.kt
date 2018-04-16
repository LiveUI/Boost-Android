package io.liveui.boost.common.vmfactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.workspace.all.WorkspaceListViewModel


class WorkspaceModelFactory constructor(private val workspaceDao: WorkspaceDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkspaceListViewModel::class.java)) {
            return WorkspaceListViewModel(workspaceDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}