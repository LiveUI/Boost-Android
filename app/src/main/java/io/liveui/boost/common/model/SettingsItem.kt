package io.liveui.boost.common.model

import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.BoostFragment

data class SettingsItem(val name: String, val action: Action) {

    class Action(val activityClass: Class<out BoostActivity>?,
                 val activityData: Pair<String, Any>? = null,
                 val fragmentClass: Class<out BoostFragment>?,
                 val fragmentData: Pair<String, Any>? = null)
}