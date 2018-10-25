package io.liveui.boost.ui.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.common.model.LayoutManagerConfig
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OverviewViewModel(private val boostApiUseCase: BoostApiUseCase) : LifecycleViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val overviewData: MutableLiveData<MutableList<AppOverview>> = MutableLiveData()

    val activeOverview: MutableLiveData<AppOverview> = MutableLiveData()

    val layoutType: MutableLiveData<LayoutManagerConfig> = MutableLiveData()

    override fun onCleared() {
        disposable.clear()
    }

    fun loadAppsOverview() {
        addDisposable(boostApiUseCase.appsOverview()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    overviewData.postValue(it)
                }, {
                    it.printStackTrace()
                }))
    }

    fun loadTeamAppsOverview(id: String) {
        addDisposable(boostApiUseCase.teamAppsOverview(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    overviewData.postValue(it)
                }, {
                    it.printStackTrace()
                }))
    }

    fun changeLayoutManager(phone: Boolean, list: Boolean) {
        layoutType.postValue(LayoutManagerConfig.getType(phone, list))
    }

    fun showList() {
        layoutType.postValue(LayoutManagerConfig.PHONE_LIST)
    }

    fun showGrid() {
        layoutType.postValue(LayoutManagerConfig.PHONE_GRID)
    }
}