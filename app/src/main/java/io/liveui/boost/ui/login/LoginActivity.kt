package io.liveui.boost.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.intro.ChooseServerFragment
import io.liveui.boost.util.ext.setNavigator
import io.liveui.boost.util.navigation.FragmentNavigationItem
import javax.inject.Inject

class LoginActivity : BoostActivity() {

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var navigationViewModel: NavigationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        navigationViewModel = ViewModelProviders.of(this, uiViewModelFactory).get(NavigationViewModel::class.java)

        setNavigator(navigatorViewModel = navigationViewModel, mainIdRes = R.id.fragment_container)

        navigationViewModel.mainNavigator.replaceFragment(FragmentNavigationItem(ChooseServerFragment::class.java))
    }
}
