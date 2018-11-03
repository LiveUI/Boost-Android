package io.liveui.boost.ui.apps

import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.IApp
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.download.DownloadStatus
import io.liveui.boost.download.FileInfo
import io.liveui.boost.ui.overview.list.BaseAppViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.IntentUtil
import io.liveui.boost.util.glide.GlideProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class DownloadAppViewModel<T : IApp> constructor(val downloadManager: BoostDownloadManager,
                                                          val ioUtil: IOUtil,
                                                          glideProvider: GlideProvider,
                                                          contextProvider: ContextProvider) : BaseAppViewModel<T>(glideProvider, contextProvider) {

    init {
        _app.observeForever {
            getAppDownloadStatus(it.getAppId())
        }
    }

    val downloadStatus = MutableLiveData<DownloadStatus>()

    val downloadProgress = MutableLiveData<Int>()

    val apkExists = Transformations.map(downloadStatus) { status ->
        if (app != null && status == DownloadStatus.COMPLETED) {
            LiveDataReactiveStreams.fromPublisher(isAppDownloaded(app!!.getAppId()).toFlowable(BackpressureStrategy.BUFFER))
        } else {
            MutableLiveData<Boolean>().apply {
                value = false
            }
        }
    }

    val isAppDownloaded = Transformations.map(downloadStatus) {
        it == DownloadStatus.COMPLETED
    }

    val isAppDownloadInProgress = Transformations.map(downloadStatus) {
        arrayListOf(DownloadStatus.IN_PROGRESS, DownloadStatus.TOKEN_VERIFICATION_STARTED, DownloadStatus.TOKEN_VERIFICATION_COMPLETE).contains(it)
    }

    val isAppDownloadFailed = Transformations.map(downloadStatus) {
        arrayListOf(DownloadStatus.TOKEN_VERIFICATION_FAILED, DownloadStatus.FAILED)
    }

    val isApkDownloadPaused = Transformations.map(downloadStatus) {
        it == DownloadStatus.PAUSED
    }

    val isApkDownloadIdle = Transformations.map(downloadStatus) {
        arrayListOf(DownloadStatus.NONE, DownloadStatus.FAILED).contains(it)
    }

    fun downloadApp() {
        app?.let {
            addDisposable(downloadManager.systemDownloader.createDownloadItem(it.getAppId()).downloadStatusPublisher.subscribe {
                downloadStatus.postValue(it)
            })
            downloadManager.downloadApp(it.getAppId())
        }
    }

    fun getAppDownloadStatus(appId: String) {
        getDownloadMemoryStatus(appId)?.subscribe()
                ?: getDownloadStorageStatus(appId).subscribe()
    }

    fun getDownloadMemoryStatus(appId: String): Observable<DownloadStatus>? {
        downloadManager.downloads.downloads[appId]?.downloadStatus?.let {
            downloadStatus.postValue(it)
        }
        return downloadManager.downloads.downloads[appId]?.downloadStatusPublisher?.doOnNext {
            downloadStatus.postValue(it)
        }
    }

    fun getDownloadStorageStatus(appId: String): Observable<FileInfo> {
        return ioUtil.getApkInfoFile(appId).doOnNext { fileInfo ->
            fileInfo.let {
                downloadStatus.postValue(it.status)
            }
        }.doOnError {
            downloadStatus.postValue(DownloadStatus.NONE)
        }
    }

    fun isAppDownloaded(appId: String): Observable<Boolean> {
        return ioUtil.isApkFileExist(appId)
    }

    fun getProgress(appId: String): PublishSubject<Int>? {
        return downloadManager.downloads.downloads[appId]?.let {
            return it.progressPublisher
        }
    }

    fun openApp() {
        app?.let { app ->
            downloadManager.downloads.downloads[app.getAppId()]?.uri?.let {
                IntentUtil.openApk(contextProvider.app, it)
            }
        }
    }
}