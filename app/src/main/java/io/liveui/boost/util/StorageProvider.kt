package io.liveui.boost.util

import android.os.Environment
import java.io.File
import javax.inject.Inject

class StorageProvider @Inject constructor() {

    fun getExternalDownloadFolder(): File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
}