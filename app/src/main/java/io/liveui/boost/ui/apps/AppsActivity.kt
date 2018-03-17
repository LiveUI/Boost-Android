package io.liveui.boost.ui.apps

import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.ext.replaceFragmentInActivity

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppsActivity: BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)
        replaceFragmentInActivity(AppsFragment(), R.id.fragment_container)
    }
}