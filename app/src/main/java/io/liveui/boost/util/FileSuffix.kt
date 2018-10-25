package io.liveui.boost.util

import androidx.annotation.StringDef

@StringDef(JSON)
@Retention(AnnotationRetention.SOURCE)
annotation class FileType

const val JSON: String = ".json"
