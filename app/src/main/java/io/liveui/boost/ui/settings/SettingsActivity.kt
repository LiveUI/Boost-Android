package io.liveui.boost.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.databinding.ActivitySettingsBinding
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.util.ext.setDatabindingContentView
import io.liveui.boost.util.ext.setNavigator
import io.liveui.boost.util.ext.setupToolbar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

class SettingsActivity : BoostActivity() {

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val toolbarVM = ViewModelProviders.of(this, uiViewModelFactory).get(ToolbarViewModel::class.java)
        navigationViewModel = ViewModelProviders.of(this, uiViewModelFactory).get(NavigationViewModel::class.java)
        setDatabindingContentView<ActivitySettingsBinding>(R.layout.activity_settings) {
            toolbarViewModel = toolbarVM
        }
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        setNavigator(navigatorViewModel = navigationViewModel, mainIdRes = R.id.fragment_container)

        navigationViewModel.mainNavigator.replaceFragment(FragmentNavigationItem(clazz = SettingsFragment::class.java))
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SettingsActivity::class.java))
        }
    }
}