package io.liveui.boost.download

import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.usecase.BoostDownloadUseCase
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.StorageProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

abstract class BaseDownloader constructor(downloadsHolder: DownloadsHolder,
                                          val downloadUseCase: BoostDownloadUseCase,
                                          val storageProvider: StorageProvider,
                                          val ioUtil: IOUtil) {
    protected val downloads = downloadsHolder.downloads
    protected val uniqueDownloadId = downloadsHolder.uniqueDownloadId

    fun getFileDownloadInfo(appId: String): Observable<AppTokenResponse> {
        return downloadUseCase.getDownloadToken(appId)
    }

    fun onCreateAppFileInfo(downloadItem: DownloadItem) {
        createFileInfo(downloadItem)
    }

    fun onAuthTokenRequested(downloadItem: DownloadItem) {
        updateFileInfo(downloadItem)
    }

    fun onAuthTokenReceived(downloadItem: DownloadItem) {
        updateFileInfo(downloadItem)
    }

    fun onDownloadStarted(downloadItem: DownloadItem) {
        updateFileInfo(downloadItem)
    }

    fun onDownloadFinish(downloadItem: DownloadItem) {
        updateFileInfo(downloadItem)
    }

    fun onDownloadError(downloadItem: DownloadItem) {
        updateFileInfo(downloadItem)
    }

    fun createFileInfo(downloadItem: DownloadItem) {
        downloadItem.let {
            ioUtil.saveJsonObjectToFile(FileInfo(it.uri), storageProvider.getExternalDownloadFolder(), downloadItem.appId)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
        }
    }

    fun updateFileInfo(downloadItem: DownloadItem) {
        val disposable = ioUtil.loadJsonObjectFromFile(storageProvider.getExternalDownloadFolder(), downloadItem.appId + ".json", FileInfo::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    it?.let {
                        updateSavedFile(it, downloadItem)
                    }
                }
    }

    fun updateSavedFile(fileInfo: FileInfo, downloadItem: DownloadItem) {
        fileInfo.apply {
            updated = Calendar.getInstance()
            status = downloadItem.downloadStatus
            uri = downloadItem.uri
        }
        ioUtil.saveJsonObjectToFile(fileInfo, storageProvider.getExternalDownloadFolder(), downloadItem.appId)
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}