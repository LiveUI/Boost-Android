package io.liveui.boost.ui

import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.databinding.ActivityMainBinding
import io.liveui.boost.ui.overview.OverviewFragment
import io.liveui.boost.ui.serverinfo.ServerInfoViewModel
import io.liveui.boost.ui.settings.SettingsActivity
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.teams.TeamsFragment
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.ui.workspace.all.WorkspaceListActivity
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setNavigator
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject


/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class MainActivity : BoostActivity(), PopupMenu.OnMenuItemClickListener {

    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var navigationViewModel: NavigationViewModel

    lateinit var teamsViewModel: TeamsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teamsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(TeamsViewModel::class.java)
        val serverInfoVM = ViewModelProviders.of(this, apiViewModelFactory).get(ServerInfoViewModel::class.java)
        val toolbarVM = ViewModelProviders.of(this, uiViewModelFactory).get(ToolbarViewModel::class.java)
        navigationViewModel = ViewModelProviders.of(this, uiViewModelFactory).get(NavigationViewModel::class.java)
        setDatabindingContentView<ActivityMainBinding>(R.layout.activity_main) {
            toolbarViewModel = toolbarVM
            serverInfoViewModel = serverInfoVM
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_sidemenu_button)
        }

        setNavigator(navigatorViewModel = navigationViewModel, mainIdRes = R.id.fragment_container, secondaryIdRes = R.id.side_menu_container)
        showAppOverviewFragment()
        showTeamsFragment()

        teamsViewModel.activeTeam.observe(this, Observer {
            toolbarVM.title.postValue(it?.name ?: userSession.workspace?.name)
            drawer_layout.closeDrawers()
        })

        teamsViewModel.teamInfo.observe(this, Observer {
            toolbarVM.subtitle.postValue("${it.apps} Applications, ${it.builds} builds")
        })

        btn_workspace.setOnClickListener {
            showWorkspaceActivity()
        }

        btn_more.setOnClickListener {
            showMoreMenu(it)
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_refresh -> teamsViewModel.loadTeams()
            R.id.action_settings -> showSettingsActivity()
            R.id.action_logout -> userSession.logout {
                SplashActivity.startActivity(this)
            }
        }
        return true
    }

    private fun showMoreMenu(v: View) {
        PopupMenu(this, v).apply {
            val inflater: MenuInflater = menuInflater
            setOnMenuItemClickListener(this@MainActivity)
            inflater.inflate(R.menu.side_menu_more, menu)
            show()
        }
    }

    private fun showAppOverviewFragment() {
        navigationViewModel.mainNavigator.replaceFragment(FragmentNavigationItem(OverviewFragment::class.java))
    }

    private fun showTeamsFragment() {
        navigationViewModel.secondaryNavigator.replaceFragment(FragmentNavigationItem(TeamsFragment::class.java))
    }

    private fun showWorkspaceActivity() {
        WorkspaceListActivity.startActivity(this)
    }

    private fun showSettingsActivity() {
        SettingsActivity.startActivity(this)
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