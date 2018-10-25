package io.liveui.boost.ui.apps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.liveui.boost.api.model.App
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AppsViewModel constructor(private val apiUseCase: BoostApiUseCase) : LifecycleViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val apps: MutableLiveData<MutableList<App>> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    fun getAllApps() {
        addDisposable(apiUseCase.getApps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext{
                    apps.value = it
                    loadingStatus.value = false
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
                .subscribe()
        )
    }

    fun getFilteredApps(identifier: String?) {
        addDisposable(apiUseCase.filter(identifier = identifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext{
                    apps.value = it
                    loadingStatus.value = false
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
                .subscribe()
        )
    }
}