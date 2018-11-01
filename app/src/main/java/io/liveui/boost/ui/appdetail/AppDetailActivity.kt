package io.liveui.boost.ui.appdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.common.EXTRA_APP_ID
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.databinding.ActivityAppDetailBinding
import io.liveui.boost.databinding.ActivityAppsBinding
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.activity_app_detail.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject
import javax.inject.Named

class AppDetailActivity : BoostActivity() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    @field:[Inject ActivityScope Named(MAIN_NAVIGATOR)]
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbarVM = ViewModelProviders.of(this, apiViewModelFactory).get(ToolbarViewModel::class.java)
        setDatabindingContentView<ActivityAppDetailBinding>(R.layout.activity_app_detail) {
            toolbarViewModel = toolbarVM
        }
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }

        initMainNavigator()
        showAppDetailFragment()
    }

    private fun initMainNavigator() {
        mainNavigator.apply {
            fragmentManager = supportFragmentManager
            containerId = R.id.fragment_container
        }
    }

    private fun showAppDetailFragment() {
        mainNavigator.replaceFragment(FragmentNavigationItem(clazz = AppDetailFragment::class.java, args = intent.extras))
    }

    companion object {
        fun startActivity(context: Context?, appId: String?) {
            val intent = Intent(context, AppDetailActivity::class.java)
            intent.putExtra(EXTRA_APP_ID, appId)
            context?.startActivity(intent)
        }
    }
}
