package io.liveui.boost.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.MenuItem
import io.liveui.boost.R
import io.liveui.boost.util.ext.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.GravityCompat
import android.view.Gravity
import android.view.Menu
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.overview.OverviewFragment
import io.liveui.boost.ui.teams.TeamsFragment
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.ui.workspace.all.WorkspaceListFragment
import javax.inject.Inject


/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class MainActivity : BoostActivity() {

    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        replaceFragmentInActivity(OverviewFragment(), R.id.fragment_container)
        initSideMenu()

        ViewModelProviders.of(this, apiViewModelFactory).get(TeamsViewModel::class.java)
                .activeTeam.observe(this, Observer {
            drawer_layout.closeDrawers()
        })

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

    private fun initSideMenu() {
        side_menu_toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_workspace -> {
                    invalidateSideMenu(replaceFragmentInActivity(WorkspaceListFragment(), R.id.side_menu_container))
                    true
                }
                else -> false
            }
        }
        side_menu_toolbar.setNavigationOnClickListener({
            invalidateSideMenu(replaceFragmentInActivity(TeamsFragment(), R.id.side_menu_container))
        })

        invalidateSideMenu(replaceFragmentInActivity(TeamsFragment(), R.id.side_menu_container))
    }

    private fun invalidateSideMenu(fragment: Fragment) {
        when (fragment) {
            is TeamsFragment -> {
                side_menu_toolbar.inflateMenu(R.menu.side_menu_teams)
                side_menu_toolbar.navigationIcon = null
            }
            is WorkspaceListFragment -> {
                side_menu_toolbar.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, null)
                side_menu_toolbar.menu.clear()
            }
        }
    }
}