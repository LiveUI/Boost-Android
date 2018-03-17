package io.liveui.boost.ui.apps

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.liveui.boost.api.model.App
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AppsViewModel constructor(private val apiUseCase: BoostApiUseCase) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val apps: MutableLiveData<MutableList<App>> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun getApps(platform: String, identifier: String) {
        disposables.add(apiUseCase.getApps(platform, identifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .subscribe({ result ->
                    loadingStatus.value = false
                    apps.value = result
                }, { e ->
                    loadingStatus.value = false
                    exception.value = e
                })
        )
    }
}