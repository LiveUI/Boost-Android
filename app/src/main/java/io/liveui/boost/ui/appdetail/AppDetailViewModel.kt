package io.liveui.boost.ui.appdetail

import androidx.lifecycle.MutableLiveData
import io.liveui.boost.api.model.App
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.apps.DownloadAppViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import javax.inject.Inject


class AppDetailViewModel @Inject constructor(private val apiUseCase: BoostApiUseCase,
                                             downloadManager: BoostDownloadManager,
                                             glideProvider: GlideProvider,
                                             ioUtil: IOUtil,
                                             contextProvider: ContextProvider)
    : DownloadAppViewModel<App>(downloadManager, ioUtil, glideProvider, contextProvider) {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    fun downloadAppDetail(id: String) {
        addDisposable(apiUseCase.getApp(id)
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    app = it
                }
                .subscribe()
        )
    }
}