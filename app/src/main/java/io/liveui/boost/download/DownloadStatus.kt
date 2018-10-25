package io.liveui.boost.download

import android.app.DownloadManager

enum class DownloadStatus {
    NONE,
    TOKEN_VERIFICATION_STARTED,
    TOKEN_VERIFICATION_COMPLETE,
    TOKEN_VERIFICATION_FAILED,
    PENDING,
    PAUSED,
    IN_PROGRESS,
    COMPLETED,
    FAILED;

    companion object {
        fun getByDownloadManagerStatus(status: Int): DownloadStatus {
            return when (status) {
                DownloadManager.STATUS_FAILED -> FAILED
                DownloadManager.STATUS_SUCCESSFUL -> COMPLETED
                DownloadManager.STATUS_PAUSED -> PAUSED
                DownloadManager.STATUS_PENDING -> PENDING
                DownloadManager.STATUS_RUNNING -> IN_PROGRESS
                else -> NONE
            }
        }
    }
}