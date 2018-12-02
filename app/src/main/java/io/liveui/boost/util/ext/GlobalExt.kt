package io.liveui.boost.util.ext

import android.graphics.Color

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
