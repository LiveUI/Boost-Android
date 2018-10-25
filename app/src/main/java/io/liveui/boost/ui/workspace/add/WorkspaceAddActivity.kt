package io.liveui.boost.ui.workspace.add

import android.os.Bundle
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import javax.inject.Inject

class WorkspaceAddActivity : BoostActivity() {

    @Inject
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainNavigator.containerId = android.R.id.content
        mainNavigator.replaceFragment(FragmentNavigationItem(clazz = WorkspaceAddFragment::class.java))
    }
}