package io.liveui.boost.download

import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadsHolder @Inject constructor() {
    val downloads = HashMap<String, DownloadItem>()
    val uniqueDownloadId = AtomicLong(0)
}