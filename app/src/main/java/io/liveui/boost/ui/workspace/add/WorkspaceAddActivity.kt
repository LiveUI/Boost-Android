package io.liveui.boost.ui.workspace.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.databinding.ActivityWorkspaceAddBinding
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.UiActivityViewModel
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setNavigator
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class WorkspaceAddActivity : BoostActivity() {

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbarVM = ViewModelProviders.of(this, uiViewModelFactory).get(ToolbarViewModel::class.java)
        val uiActivityVM = ViewModelProviders.of(this, uiViewModelFactory).get(UiActivityViewModel::class.java)
        navigationViewModel = ViewModelProviders.of(this, uiViewModelFactory).get(NavigationViewModel::class.java)
        setDatabindingContentView<ActivityWorkspaceAddBinding>(R.layout.activity_workspace_add) {
            toolbarViewModel = toolbarVM
            uiViewModel = uiActivityVM
        }
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        setNavigator(navigatorViewModel = navigationViewModel, mainIdRes = R.id.fragment_container)

        navigationViewModel.mainNavigator.replaceFragment(FragmentNavigationItem(clazz = WorkspaceAddFragment::class.java))
    }

    companion object {

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, WorkspaceAddActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}