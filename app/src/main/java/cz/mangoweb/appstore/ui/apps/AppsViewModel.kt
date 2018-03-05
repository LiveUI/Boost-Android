package cz.mangoweb.appstore.ui.apps

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cz.mangoweb.appstore.api.model.App
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AppsViewModel constructor(private val apiUseCase: BoostApiUseCase) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    private val apps: MutableLiveData<List<App>> = MutableLiveData()

    private val app: MutableLiveData<App> = MutableLiveData()

    private val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun getLoadingStatus(): MutableLiveData<Boolean> {
        return loadingStatus
    }

    fun getException(): MutableLiveData<Throwable> {
        return exception
    }

    fun getApps(platform: String, identifier: String) {
        disposables.add(apiUseCase.getApps(platform, identifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> apps.value = result },
                        { e -> exception.value = e })
        )
    }

    fun getApp(id: Int, level: Int) {
        disposables.add(apiUseCase.getApp(id, level)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> app.value = result },
                        { e -> exception.value = e })
        )
    }
}