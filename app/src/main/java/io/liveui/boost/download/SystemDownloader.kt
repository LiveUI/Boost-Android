package io.liveui.boost.download

import android.app.DownloadManager
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.usecase.BoostDownloadUseCase
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.StorageProvider
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SystemDownloader @Inject constructor(val downloadManager: DownloadManager,
                                           val sharedPreferences: SharedPreferences,
                                           downloadsHolder: DownloadsHolder,
                                           downloadUseCase: BoostDownloadUseCase,
                                           storageProvider: StorageProvider,
                                           ioUtil: IOUtil) : BaseDownloader(downloadsHolder, downloadUseCase, storageProvider, ioUtil) {

    var updatesAllowed = true

    fun downloadFile(appId: String): Observable<DownloadItem>? {
        return downloadAppInfo(appId)?.switchMap {
            startDownload(appId, it.file)
            requestUpdates(appId, 500)
        }
    }

    fun createDownloadItem(appId: String): DownloadItem {
        val downloadItem = DownloadItem(id = uniqueDownloadId.getAndIncrement(), appId = appId, downloadStatus = DownloadStatus.NONE)
        downloads[appId] = downloadItem
        onCreateAppFileInfo(downloadItem)
        return downloadItem
    }

    fun downloadAppInfo(appId: String): Observable<AppTokenResponse>? {
        return getFileDownloadInfo(appId).doOnSubscribe {
            downloads[appId]?.apply {
                downloadStatus = DownloadStatus.TOKEN_VERIFICATION_STARTED
                onAuthTokenRequested(this)
            }
        }.doOnNext { appTokenResponse ->
            downloads[appId]?.apply {
                downloadStatus = DownloadStatus.TOKEN_VERIFICATION_COMPLETE
                serverAppId = appTokenResponse.app_id
                serverAppToken = appTokenResponse.token
                serverPathFile = appTokenResponse.file
                serverPathios = appTokenResponse.ios
                serverPathPlist = appTokenResponse.plist
                onAuthTokenReceived(this)
            }
        }.doOnError {
            downloads[appId]?.apply {
                downloadStatus = DownloadStatus.TOKEN_VERIFICATION_FAILED
                onDownloadError(this)
            }
        }
    }

    fun startDownload(appId: String, url: String) {
        downloads[appId]?.apply {
            downloadStatus = DownloadStatus.PENDING
            id = enqueDownload(url)
            onDownloadStarted(this)
        }
    }

    fun requestUpdates(appId: String, interval: Long): Observable<DownloadItem> {
        return getDownloadStatusObservable(downloads[appId]!!)
                .delay(interval, TimeUnit.MILLISECONDS)
                .repeat()
                .takeUntil { updatedItem ->
                    repeatUntil(updatedItem)
                }
                .doOnNext {
                    onDownloadFinish(it)
                }
    }

    fun repeatUntil(downloadItem: DownloadItem): Boolean {
        if (!updatesAllowed) {
            return false
        }

        return when (downloadItem.downloadStatus) {
            DownloadStatus.FAILED -> false
            else -> {
                downloadItem.downloadStatus == DownloadStatus.COMPLETED
            }
        }
    }

    fun enqueDownload(url: String): Long {
        val request = DownloadManager.Request(Uri.parse(url.replace("http://localhost:8080/", "http://10.0.2.2:8080/"))) //TODO remove
        request.addRequestHeader("Authorization", sharedPreferences.getString("jwtToken", null))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        request.setDestinationUri(storageProvider.getExternalDownloadFolder().toUri())
        return downloadManager.enqueue(request)
    }

    fun getDownloadStatusObservable(downloadItem: DownloadItem): Observable<DownloadItem> {
        return Observable.create<DownloadItem> { emiter ->
            val query = DownloadManager.Query()
            query.setFilterById(downloadItem.id)
            Timber.d("Start query")
            try {
                val cursor: Cursor? = downloadManager.query(query)

                cursor.use {
                    if (cursor == null || !cursor.moveToFirst()) {
                        emiter.onError(DownloadException(""))
                    } else {
                        val bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        val bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        val downloadPercent = (bytesDownloaded / bytesTotal * 100)
                        val downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                        downloadItem.progress = downloadPercent
                        downloadItem.downloadStatus = DownloadStatus.getByDownloadManagerStatus(downloadStatus)

                        when (downloadItem.downloadStatus) {
                            DownloadStatus.FAILED -> {
                                emiter.onError(DownloadException("Download failed"))
                            }
                            DownloadStatus.COMPLETED -> {
                                downloadItem.uri = downloadManager.getUriForDownloadedFile(downloadItem.id)
                                emiter.onNext(downloadItem)
                                emiter.onComplete()
                            }
                            else -> {
                                emiter.onComplete()
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                emiter.onError(DownloadException(""))
            }
        }
    }

}