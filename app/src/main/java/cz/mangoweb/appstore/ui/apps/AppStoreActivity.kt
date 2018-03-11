package cz.mangoweb.appstore.ui.apps

import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.ui.BoostActivity
import cz.mangoweb.appstore.util.ext.replaceFragmentInActivity

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppStoreActivity: BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)
        replaceFragmentInActivity(AppsFragment(), R.id.fragment_container)
    }
}