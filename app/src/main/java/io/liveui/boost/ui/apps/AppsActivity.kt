package io.liveui.boost.ui.apps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.databinding.ActivityAppsBinding
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setNavigator
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.activity_apps.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppsActivity : BoostActivity() {

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbarVM = ViewModelProviders.of(this, uiViewModelFactory).get(ToolbarViewModel::class.java)
        navigationViewModel = ViewModelProviders.of(this, uiViewModelFactory).get(NavigationViewModel::class.java)
        setDatabindingContentView<ActivityAppsBinding>(R.layout.activity_apps) {
            toolbarViewModel = toolbarVM
        }
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        setNavigator(navigatorViewModel = navigationViewModel, mainIdRes = R.id.fragment_container, secondaryIdRes = R.id.side_menu_container)

        showAppsFragment()
    }

    private fun showAppsFragment() {
        navigationViewModel.mainNavigator.replaceFragment(FragmentNavigationItem(clazz = AppsFragment::class.java,
                args = Bundle().apply {
                    putString(EXTRA_APP, intent.getStringExtra(EXTRA_APP))
                }))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                    drawer_layout.closeDrawer(GravityCompat.END)
                } else {
                    drawer_layout.openDrawer(GravityCompat.END)
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
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val EXTRA_APP = "extra-app"

        fun startActivity(context: Context, identifier: String) {
            context.startActivity(Intent(context, AppsActivity::class.java).putExtra(EXTRA_APP, identifier).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}