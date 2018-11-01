package io.liveui.boost.ui.workspace.add

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.databinding.ActivityWorkspaceAddBinding
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject
import javax.inject.Named

class WorkspaceAddActivity : BoostActivity() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    @field:[Inject ActivityScope Named(MAIN_NAVIGATOR)]
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbarVM = ViewModelProviders.of(this, apiViewModelFactory).get(ToolbarViewModel::class.java)
        setDatabindingContentView<ActivityWorkspaceAddBinding>(R.layout.activity_workspace_add) {
            toolbarViewModel = toolbarVM
        }
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        initMainNavigator()
        mainNavigator.replaceFragment(FragmentNavigationItem(clazz = WorkspaceAddFragment::class.java))
    }

    private fun initMainNavigator() {
        mainNavigator.apply {
            fragmentManager = supportFragmentManager
            containerId = R.id.fragment_container
        }
    }
}