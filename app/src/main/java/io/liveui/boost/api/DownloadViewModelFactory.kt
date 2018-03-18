package io.liveui.boost.api

import android.arch.lifecycle.ViewModel
import io.liveui.boost.api.usecase.BoostDownloadUseCase
import io.reactivex.disposables.CompositeDisposable

class DownloadViewModelFactory constructor(private val downloadUseCase: BoostDownloadUseCase): ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun downloadApp(appId: String) {
        val downloadToken = downloadUseCase.getDownloadToken(appId)
//        downloadUseCase.downloadApp(appId, downloadToken?)
    }
}