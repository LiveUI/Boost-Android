package io.liveui.boost.ui.apps

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.activity_apps.*
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppsActivity : BoostActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainNavigator.replaceFragment(FragmentNavigationItem(clazz = AppsFragment::class.java))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                if (drawer_layout.isDrawerOpen(Gravity.END)) {
                    drawer_layout.closeDrawer(Gravity.END)
                } else {
                    drawer_layout.openDrawer(Gravity.END)
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_apps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.END)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}