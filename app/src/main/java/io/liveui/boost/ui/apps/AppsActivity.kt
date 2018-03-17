package io.liveui.boost.ui.apps

import android.os.Bundle
import android.view.MenuItem
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.ext.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_apps.*
import android.support.v4.view.GravityCompat
import io.liveui.boost.UserSession
import kotlinx.android.synthetic.main.navigation_header.view.*
import javax.inject.Inject


/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppsActivity : BoostActivity() {

    @Inject
    lateinit var userSession: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        navigation_view.getHeaderView(0).user_full_name.text = userSession.user.value?.getFullName()
        navigation_view.getHeaderView(0).user_email.text = userSession.user.value?.email

        navigation_view.setNavigationItemSelectedListener({ menuItem: MenuItem ->
            drawer_layout.closeDrawers()
            when (menuItem.itemId) {
                R.id.nav_apps -> {
                    replaceFragmentInActivity(AppsFragment(), R.id.fragment_container)
                    true
                }
                R.id.nav_my_account -> {
                    true
                }
                R.id.nav_api_keys -> {
                    true
                }
                R.id.nav_settings -> {
                    true
                }
                else -> {
                    false
                }
            }
        })

        navigation_view.setCheckedItem(R.id.nav_apps)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}