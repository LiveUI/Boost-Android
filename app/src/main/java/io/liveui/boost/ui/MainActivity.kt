package io.liveui.boost.ui

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.overview.OverviewFragment
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.util.ext.setupView
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class MainActivity : BoostActivity() {

    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    @Inject
    @ActivityScope
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
           setDisplayHomeAsUpEnabled(true)
           setHomeAsUpIndicator(R.drawable.ic_sidemenu_button)
        }
        val teamsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(TeamsViewModel::class.java)

        mainNavigator.fragmentManager = supportFragmentManager
        mainNavigator.containerId = R.id.fragment_container

        teamsViewModel.activeTeam.observe(this, Observer {
            toolbar.title = it?.name
            drawer_layout.closeDrawers()
        })

        teamsViewModel.teamInfo.observe(this, Observer {
            toolbar.subtitle = "${it.apps} Applications, ${it.builds} builds"
        })


        mainNavigator.replaceFragment(FragmentNavigationItem(OverviewFragment::class.java))
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

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(Gravity.START)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

}