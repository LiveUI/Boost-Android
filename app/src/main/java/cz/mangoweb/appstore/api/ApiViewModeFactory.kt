package cz.mangoweb.appstore.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
import cz.mangoweb.appstore.ui.apps.AppsViewModel
import cz.mangoweb.appstore.ui.teams.TeamsViewModel

class ApiViewModeFactory constructor(private val apiUseCase: BoostApiUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return with(modelClass) {
            when {
                isAssignableFrom(AppsViewModel::class.java) ->
                    AppsViewModel(apiUseCase)
                isAssignableFrom(TeamsViewModel::class.java) ->
                    TeamsViewModel(apiUseCase)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}