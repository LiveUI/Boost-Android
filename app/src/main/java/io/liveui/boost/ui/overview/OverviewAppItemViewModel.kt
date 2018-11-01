package io.liveui.boost.ui.overview

import android.content.Intent
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.App
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.ui.apps.BaseAppViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import io.reactivex.BackpressureStrategy
import javax.inject.Inject

class OverviewAppItemViewModel @Inject constructor(downloadManager: BoostDownloadManager,
                                                   glideProvider: GlideProvider,
                                                   ioUtil: IOUtil,
                                                   contextProvider: ContextProvider,
                                                   val boostApiUseCase: BoostApiUseCase)
    : BaseAppViewModel<AppOverview>(downloadManager, glideProvider, ioUtil, contextProvider) {

    val latestBuilds = Transformations.switchMap(appIdentifier) {
        LiveDataReactiveStreams.fromPublisher(boostApiUseCase.filter(identifier = it).toFlowable(BackpressureStrategy.BUFFER))
    }

    fun openAppList() {
        app?.let {
            AppsActivity.startActivity(contextProvider.app, it.getAppIdentifier())
        }
    }
}