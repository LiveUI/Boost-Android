package io.liveui.boost.ui.apps

import androidx.lifecycle.*
import io.liveui.boost.api.model.App
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AppsViewModel constructor(private val apiUseCase: BoostApiUseCase) : LifecycleViewModel() {

    val activeIdentifier = MutableLiveData<String?>()
    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()
    val exception: MutableLiveData<Throwable> = MutableLiveData()
    val apps: LiveData<MutableList<App>> = Transformations.switchMap(activeIdentifier) { identifier ->
        identifier?.let { packageName ->
            getFilteredApps(packageName)
        } ?: getAllApps()
    }

    private fun getAllApps(): LiveData<MutableList<App>> {
        return LiveDataReactiveStreams.fromPublisher(apiUseCase.getApps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }.toFlowable(BackpressureStrategy.BUFFER)
        )
    }

    private fun getFilteredApps(identifier: String): LiveData<MutableList<App>> {
        return LiveDataReactiveStreams.fromPublisher(apiUseCase.filter(identifier = identifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }.toFlowable(BackpressureStrategy.BUFFER))
    }
}