package io.liveui.boost.ui.overview

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.api.model.IApp
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.ui.apps.BaseAppViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OverviewListItemViewModel @Inject constructor(downloadManager: BoostDownloadManager,
                                                    glideProvider: GlideProvider,
                                                    ioUtil: IOUtil,
                                                    contextProvider: ContextProvider,
                                                    val boostApiUseCase: BoostApiUseCase)
    : BaseAppViewModel<IApp>(downloadManager, glideProvider, ioUtil, contextProvider) {

    val latestBuilds = Transformations.switchMap(appIdentifier) {
        LiveDataReactiveStreams.fromPublisher(boostApiUseCase.filter(identifier = it, limit = 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable(BackpressureStrategy.BUFFER))
    }

    fun openAppList() {
        app?.let {
            AppsActivity.startActivity(contextProvider.app, it.getAppIdentifier())
        }
    }

    fun openAppDetail() {
        app?.let {
            AppDetailActivity.startActivity(contextProvider.app, it.getAppId())
        }
    }
}