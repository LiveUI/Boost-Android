package io.liveui.boost.common.vmfactory

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.ui.appdetail.AppDetailViewModel
import io.liveui.boost.ui.apps.AppsViewModel
import io.liveui.boost.ui.overview.OverviewViewModel
import io.liveui.boost.ui.teams.TeamsViewModel

class ApiViewModeFactory constructor(private val apiUseCase: BoostApiUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return with(modelClass) {
            when {
                isAssignableFrom(AppsViewModel::class.java) ->
                    AppsViewModel(apiUseCase)
                isAssignableFrom(TeamsViewModel::class.java) ->
                    TeamsViewModel(apiUseCase)
                isAssignableFrom(AppDetailViewModel::class.java) ->
                        AppDetailViewModel(apiUseCase)
                isAssignableFrom(OverviewViewModel::class.java) ->
                    OverviewViewModel(apiUseCase)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}