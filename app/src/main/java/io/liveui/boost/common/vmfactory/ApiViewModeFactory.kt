package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.appdetail.AppDetailViewModel
import io.liveui.boost.ui.apps.AppsViewModel
import io.liveui.boost.ui.overview.OverviewViewModel
import io.liveui.boost.ui.register.RegisterViewModel
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider

//TODO rewrite to DI
class ApiViewModeFactory constructor(private val apiUseCase: BoostApiUseCase,
                                     val downloadManager: BoostDownloadManager,
                                     val glideProvider: GlideProvider,
                                     val ioUtil: IOUtil,
                                     val contextProvider: ContextProvider) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(AppsViewModel::class.java) ->
                    AppsViewModel(apiUseCase)
                isAssignableFrom(TeamsViewModel::class.java) ->
                    TeamsViewModel(apiUseCase)
                isAssignableFrom(AppDetailViewModel::class.java) ->
                    AppDetailViewModel(apiUseCase, downloadManager, glideProvider, ioUtil, contextProvider)
                isAssignableFrom(OverviewViewModel::class.java) ->
                    OverviewViewModel(apiUseCase, contextProvider)
                isAssignableFrom(RegisterViewModel::class.java) ->
                    RegisterViewModel(apiUseCase)
                isAssignableFrom(ToolbarViewModel::class.java) ->
                    ToolbarViewModel(contextProvider)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
    }
}