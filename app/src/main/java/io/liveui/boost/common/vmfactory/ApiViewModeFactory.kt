package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.appdetail.AppDetailViewModel
import io.liveui.boost.ui.apps.AppsViewModel
import io.liveui.boost.ui.overview.OverviewViewModel
import io.liveui.boost.ui.register.RegisterViewModel
import io.liveui.boost.ui.teams.TeamsViewModel
import javax.inject.Provider

//TODO rewrite to DI
class ApiViewModeFactory constructor(val appViewModelProvider: Provider<AppsViewModel>,
                                     val teamsViewModel: Provider<TeamsViewModel>,
                                     val appDetailViewModel: Provider<AppDetailViewModel>,
                                     val overviewViewModel: Provider<OverviewViewModel>,
                                     val registerViewModel: Provider<RegisterViewModel>,
                                     val toolbarViewModel: Provider<ToolbarViewModel>) : ViewModelProvider.Factory {

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
                isAssignableFrom(ToolbarViewModel::class.java) ->
                    toolbarViewModel.get()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}