package io.liveui.boost.ui.overview.list

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.glide.GlideProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OverviewListItemViewModel @Inject constructor(glideProvider: GlideProvider,
                                contextProvider: ContextProvider,
                                val boostApiUseCase: BoostApiUseCase) : BaseAppViewModel<AppOverview>(glideProvider, contextProvider) {

    val latestBuilds = Transformations.switchMap(appIdentifier) {
        LiveDataReactiveStreams.fromPublisher(boostApiUseCase.filter(identifier = it, limit = 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable(BackpressureStrategy.BUFFER))
    }
}