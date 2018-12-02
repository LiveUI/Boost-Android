package io.liveui.boost.ui.workspace.all

import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.serverinfo.BaseServerInfoViewModel
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.DensityIconMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkspaceListItemViewModel @Inject constructor(boostCheckUseCase: BoostCheckUseCase,
                                                     densityIconMapper: DensityIconMapper,
                                                     val contextProvider: ContextProvider,
                                                     val workspaceDao: WorkspaceDao) : BaseServerInfoViewModel(boostCheckUseCase, densityIconMapper) {

    //TODO fix
    fun changeWorkspace(workspace: Workspace) = GlobalScope.launch {
        workspaceDao.setActive(workspace)
        SplashActivity.startActivity(contextProvider.app)
    }

    fun onItemClick(onServerVerified: () -> Unit) {
        val status = workspace.value?.status
        val inactive = workspace.value?.active == 0
        val context = contextProvider.app
        when (status) {
            Workspace.Status.NEW -> {
                WorkspaceAddActivity.startActivity(context)
            }
            Workspace.Status.SERVER_VERIFIED -> {
                onServerVerified()
            }
            Workspace.Status.ACTIVATED -> {
                if (inactive) {
                    changeWorkspace(workspace.value!!)
                }
            }
        }
    }

}