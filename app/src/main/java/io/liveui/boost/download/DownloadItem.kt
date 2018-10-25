package io.liveui.boost.download

import android.net.Uri
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class DownloadItem(var id: Long,
                   val appId: String,
                   var serverAppToken: String? = null,
                   var serverPathFile: String? = null,
                   var serverPathios: String? = null,
                   var serverAppId: String? = null,
                   var serverPathPlist: String? = null,
                   var uri: Uri? = null,
                   downloadStatus: DownloadStatus = DownloadStatus.NONE,
                   progress: Int = -1) {

    var downloadStatus: DownloadStatus = downloadStatus
        set(value) {
            field = value
            when (value) {
                DownloadStatus.COMPLETED, DownloadStatus.FAILED, DownloadStatus.TOKEN_VERIFICATION_FAILED -> {
                    downloadStatusPublisher.onNext(value)
                    downloadStatusPublisher.onComplete()
                }
                else -> {
                    downloadStatusPublisher.onNext(value)
                }
            }
        }

    var progress: Int = progress
        set(value) {
            field = value
            if (downloadStatus == DownloadStatus.IN_PROGRESS) {
                progressPublisher.onNext(value)
            }
        }

    val downloadStatusPublisher: BehaviorSubject<DownloadStatus> = BehaviorSubject.create()
    val progressPublisher: PublishSubject<Int> = PublishSubject.create()
}