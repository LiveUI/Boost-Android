package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.ui.appdetail.AppDetailViewModel
import io.liveui.boost.ui.apps.AppsViewModel
import io.liveui.boost.ui.overview.OverviewViewModel
import io.liveui.boost.ui.register.RegisterViewModel
import io.liveui.boost.ui.serverinfo.ServerInfoViewModel
import io.liveui.boost.ui.teams.TeamsViewModel
import javax.inject.Inject
import javax.inject.Provider

class ApiViewModeFactory @Inject constructor(private val appViewModelProvider: Provider<AppsViewModel>,
                                             private val teamsViewModel: Provider<TeamsViewModel>,
                                             private val appDetailViewModel: Provider<AppDetailViewModel>,
                                             private val overviewViewModel: Provider<OverviewViewModel>,
                                             private val registerViewModel: Provider<RegisterViewModel>,
                                             private val serverInfoViewModel: Provider<ServerInfoViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(AppsViewModel::class.java) ->
                    appViewModelProvider.get()
                isAssignableFrom(TeamsViewModel::class.java) ->
                    teamsViewModel.get()
                isAssignableFrom(AppDetailViewModel::class.java) ->
                    appDetailViewModel.get()
                isAssignableFrom(OverviewViewModel::class.java) ->
                    overviewViewModel.get()
                isAssignableFrom(RegisterViewModel::class.java) ->
                    registerViewModel.get()
                isAssignableFrom(ServerInfoViewModel::class.java) ->
                    serverInfoViewModel.get()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}