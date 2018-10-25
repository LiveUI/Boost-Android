package io.liveui.boost.ui.appdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.liveui.boost.api.model.App
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.apps.AppsItemViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.glide.GlideProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AppDetailViewModel constructor(private val apiUseCase: BoostApiUseCase,
                                     downloadManager: BoostDownloadManager,
                                     glideProvider: GlideProvider,
                                     ioUtil: IOUtil,
                                     contextProvider: ContextProvider) : AppsItemViewModel(downloadManager, glideProvider, ioUtil, contextProvider) {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val appInfo: MutableLiveData<App> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    fun getApp(id: String) {
        addDisposable(apiUseCase.getApp(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                }
                .subscribe({ result -> appInfo.value = result },
                        { e -> exception.value = e })
        )
    }
}