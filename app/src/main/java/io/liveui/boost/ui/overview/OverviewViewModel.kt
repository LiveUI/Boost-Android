package io.liveui.boost.ui.overview

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.api.model.Team
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OverviewViewModel(private val boostApiUseCase: BoostApiUseCase) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val overviewData: MutableLiveData<MutableList<AppOverview>> = MutableLiveData()

    val activeOverview: MutableLiveData<AppOverview> = MutableLiveData()


    override fun onCleared() {
        disposable.clear()
    }

    fun loadAppsOverview() {
        disposable.add(boostApiUseCase.appsOverview()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    overviewData.postValue(it)
                }, {

                }))
    }

    fun loadTeamAppsOverview(id: String) {
        disposable.add(boostApiUseCase.teamAppsOverview(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    overviewData.postValue(it)
                }, {

                }))
    }
}