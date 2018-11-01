package io.liveui.boost.ui.apps

import android.widget.ImageView
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.App
import io.liveui.boost.api.model.IApp
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.download.DownloadStatus
import io.liveui.boost.download.FileInfo
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.IntentUtil
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.glide.GlideProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

abstract class BaseAppViewModel<T: IApp> constructor(val downloadManager: BoostDownloadManager,
                                                val glideProvider: GlideProvider,
                                                val ioUtil: IOUtil,
                                                val contextProvider: ContextProvider) : LifecycleViewModel() {

    val downloadStatus = MutableLiveData<DownloadStatus>()
    val downloadProgress = MutableLiveData<Int>()
    val apkExists = Transformations.map(downloadStatus) { status ->
        if (status == DownloadStatus.COMPLETED) {
            LiveDataReactiveStreams.fromPublisher(isAppDownloaded(nonNullApp.getAppId()).toFlowable(BackpressureStrategy.BUFFER))
        } else {
            MutableLiveData<Boolean>().apply {
                value = false
            }
        }
    }

    val observableApp = MutableLiveData<T>()
    lateinit var nonNullApp: T
    var app: T? = null
        set(value) {
            field = value
            observableApp.postValue(value)
            value?.let {
                nonNullApp = it
                getAppDownloadStatus(it.getAppId())
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

    val appName = Transformations.map(observableApp) {
        it.getAppName()
    }

    val appVersion = Transformations.map(observableApp) {
        it.getAppVersion()
    }

    val appIdentifier = Transformations.map(observableApp) {
        it.getAppIdentifier()
    }

    val id = Transformations.map(observableApp) {
        it.getAppId()
    }

    fun loadAppIcon(imageView: ImageView, appId: String) {
        glideProvider.loadAppIcon(imageView, appId)
    }

    fun downloadApp() {
        addDisposable(downloadManager.systemDownloader.createDownloadItem(nonNullApp.getAppId()).downloadStatusPublisher.subscribe {
            downloadStatus.postValue(it)
        })
        downloadManager.downloadApp(nonNullApp.getAppId())
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

    fun openSettings() {
        IntentUtil.openSettings(contextProvider.app, nonNullApp.getAppIdentifier())
    }

    fun openApp() {
        downloadManager.downloads.downloads[nonNullApp.getAppId()]?.uri?.let {
            IntentUtil.openApk(contextProvider.app, it)
        }
    }
}