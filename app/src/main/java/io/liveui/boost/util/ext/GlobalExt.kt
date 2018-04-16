package io.liveui.boost.util.ext

import android.graphics.Color
import android.os.Environment
import java.io.File


fun getDownloadDir(): File {
    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile
}

fun getColor(color: String): Int {
    return try {
        Color.parseColor(color.let {
            if (!it.startsWith("#")) {
                "#$it"
            } else {
                it
            }
        })
    } catch (e: Exception) {
        Color.BLACK
    }
}
