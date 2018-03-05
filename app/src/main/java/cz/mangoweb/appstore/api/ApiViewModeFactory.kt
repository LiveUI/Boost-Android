package cz.mangoweb.appstore.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
import cz.mangoweb.appstore.ui.apps.AppsViewModel

class ApiViewModeFactory constructor(private val apiUseCase: BoostApiUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return with(modelClass) {
            when {
                isAssignableFrom(AppsViewModel::class.java) ->
                    AppsViewModel(apiUseCase)
                //TODO add api viewmodels
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}