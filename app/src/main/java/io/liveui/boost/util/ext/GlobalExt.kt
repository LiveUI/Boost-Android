package io.liveui.boost.util.ext

import android.os.Environment
import java.io.File


fun getDownloadDir(): File {
    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile
}