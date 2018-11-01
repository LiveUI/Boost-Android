package io.liveui.boost.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.databinding.ActivityMainBinding
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.overview.OverviewFragment
import io.liveui.boost.ui.settings.SettingsActivity
import io.liveui.boost.ui.teams.TeamsFragment
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.ui.workspace.all.WorkspaceListActivity
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import io.liveui.boost.util.navigation.SECONDARY_NAVIGATOR
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject
import javax.inject.Named


/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class MainActivity : BoostActivity() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    @field:[Inject ActivityScope Named(MAIN_NAVIGATOR)]
    lateinit var mainNavigator: MainNavigator

    @field:[Inject ActivityScope Named(SECONDARY_NAVIGATOR)]
    lateinit var sideNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teamsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(TeamsViewModel::class.java)
        val toolbarVM = ViewModelProviders.of(this, apiViewModelFactory).get(ToolbarViewModel::class.java)
        setDatabindingContentView<ActivityMainBinding>(R.layout.activity_main) {
            toolbarViewModel = toolbarVM
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_sidemenu_button)
        }

        initMainNavigator()
        initSideMenuNavigator()
        showAppOverviewFragment()
        showTeamsFragment()

        teamsViewModel.activeTeam.observe(this, Observer {
            toolbarVM.title.postValue(it?.name)
            drawer_layout.closeDrawers()
        })

        teamsViewModel.teamInfo.observe(this, Observer {
            toolbarVM.subtitle.postValue("${it.apps} Applications, ${it.builds} builds")
        })

        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_workspace -> {
                    WorkspaceListActivity.startActivity(this)
                    true
                }
                R.id.action_settings -> {
                    SettingsActivity.startActivity(this)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun initMainNavigator() {
        mainNavigator.apply {
            fragmentManager = supportFragmentManager
            containerId = R.id.fragment_container
        }
    }

    private fun initSideMenuNavigator() {
        sideNavigator.apply {
            fragmentManager = supportFragmentManager
            containerId = R.id.side_menu_container
        }
    }

    private fun showAppOverviewFragment() {
        mainNavigator.replaceFragment(FragmentNavigationItem(OverviewFragment::class.java))
    }

    private fun showTeamsFragment() {
        sideNavigator.replaceFragment(FragmentNavigationItem(TeamsFragment::class.java))
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
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

}