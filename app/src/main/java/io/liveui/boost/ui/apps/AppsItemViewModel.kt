package io.liveui.boost.ui.apps

import android.widget.ImageView
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

open class AppsItemViewModel @Inject constructor(val downloadManager: BoostDownloadManager,
                                            val glideProvider: GlideProvider,
                                            val ioUtil: IOUtil,
                                            val contextProvider: ContextProvider) : LifecycleViewModel() {

    val downloadStatus = MutableLiveData<DownloadStatus>()
    val downloadProgress = MutableLiveData<Int>()
    val apkExists = Transformations.map(downloadStatus) { status ->
        if (status == DownloadStatus.COMPLETED) {
            LiveDataReactiveStreams.fromPublisher(isAppDownloaded(app.getAppId()).toFlowable(BackpressureStrategy.BUFFER))
        } else {
            MutableLiveData<Boolean>().apply {
                value = false
            }
        }
    }

    lateinit var app: IApp

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
        it == DownloadStatus.NONE
    }

    fun loadAppIcon(imageView: ImageView, appId: String) {
        glideProvider.loadAppIcon(imageView, appId)
    }

    fun downloadApp() {
        addDisposable(downloadManager.systemDownloader.createDownloadItem(app.getAppId()).downloadStatusPublisher.subscribe {
            downloadStatus.postValue(it)
        })
        downloadManager.downloadApp(app.getAppId())
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

    fun getDownloadStorageStatus(appId: String): Observable<FileInfo?> {
        return ioUtil.getApkInfoFile(appId).doOnNext { fileInfo ->
            fileInfo?.let {
                downloadStatus.postValue(it.status)
            } ?: downloadStatus.postValue(DownloadStatus.NONE)
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
        IntentUtil.openSettings(contextProvider.app, app.getAppIdentifier())
    }

    fun openApp() {
        downloadManager.downloads.downloads[app.getAppId()]?.uri?.let {
            IntentUtil.openApk(contextProvider.app, it)
        }
    }

    fun openAppDetail() {
        AppDetailActivity.startActivity(contextProvider.app, app.getAppId())
    }
}