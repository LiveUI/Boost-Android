package io.liveui.boost.download

import android.net.Uri
import java.util.*

data class FileInfo(var uri: Uri? = null,
                    var status: DownloadStatus = DownloadStatus.NONE,
                    var created: Calendar = Calendar.getInstance(),
                    var updated: Calendar = Calendar.getInstance())