package io.liveui.boost.ui.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.api.model.Team
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.common.model.LayoutManagerConfig
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OverviewViewModel @Inject constructor(private val boostApiUseCase: BoostApiUseCase,
                        private val contextProvider: ContextProvider) : LifecycleViewModel() {

    val activeTeam: MutableLiveData<Team?> = MutableLiveData()
    val overviewData: LiveData<MutableList<AppOverview>> = Transformations.switchMap(activeTeam) { team ->
        team?.let {
            LiveDataReactiveStreams.fromPublisher(boostApiUseCase.teamAppsOverview(it.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).toFlowable(BackpressureStrategy.BUFFER))
        } ?: LiveDataReactiveStreams.fromPublisher(boostApiUseCase.appsOverview()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toFlowable(BackpressureStrategy.BUFFER))
    }
    val layoutManager: MutableLiveData<RecyclerView.LayoutManager> = MutableLiveData()

    fun setLayoutManager(phone: Boolean, list: Boolean) {
        val managerConfig = LayoutManagerConfig.getType(phone, list)
        layoutManager.postValue(getLayoutManagerByConfiguration(managerConfig))
    }

    fun getLayoutManagerByConfiguration(layoutManagerConfig: LayoutManagerConfig): RecyclerView.LayoutManager {
        return when (layoutManagerConfig) {
            LayoutManagerConfig.PHONE_LIST -> {
                LinearLayoutManager(contextProvider.app)
            }
            LayoutManagerConfig.PHONE_GRID -> {
                GridLayoutManager(contextProvider.app, 2)
            }
            LayoutManagerConfig.TABLET_LIST -> {
                LinearLayoutManager(contextProvider.app)
            }
            LayoutManagerConfig.TABLET_GRID -> {
                GridLayoutManager(contextProvider.app, 4)
            }
        }
    }

    fun showList() {
        setLayoutManager(true, true)
    }

    fun showGrid() {
        setLayoutManager(true, false)
    }
}