package io.liveui.boost.util.navigation

import androidx.annotation.StringDef

@StringDef(MAIN_NAVIGATOR, SECONDARY_NAVIGATOR)
@Retention(AnnotationRetention.SOURCE)
annotation class NavigatorType

const val MAIN_NAVIGATOR: String = "main_navigator"
const val SECONDARY_NAVIGATOR: String = "secondary_navigator"