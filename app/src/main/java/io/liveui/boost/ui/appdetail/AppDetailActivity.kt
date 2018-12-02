package io.liveui.boost.ui.appdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.EXTRA_APP_ID
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.databinding.ActivityAppDetailBinding
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setNavigator
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class AppDetailActivity : BoostActivity() {

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbarVM = ViewModelProviders.of(this, uiViewModelFactory).get(ToolbarViewModel::class.java)
        navigationViewModel = ViewModelProviders.of(this, uiViewModelFactory).get(NavigationViewModel::class.java)
        setDatabindingContentView<ActivityAppDetailBinding>(R.layout.activity_app_detail) {
            toolbarViewModel = toolbarVM
        }
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        setNavigator(navigatorViewModel = navigationViewModel, mainIdRes = R.id.fragment_container)

        showAppDetailFragment()
    }

    private fun showAppDetailFragment() {
        navigationViewModel.mainNavigator.replaceFragment(FragmentNavigationItem(clazz = AppDetailFragment::class.java, args = intent.extras))
    }

    companion object {
        fun startActivity(context: Context?, appId: String?) {
            context?.startActivity(Intent(context, AppDetailActivity::class.java).putExtra(EXTRA_APP_ID, appId).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}
