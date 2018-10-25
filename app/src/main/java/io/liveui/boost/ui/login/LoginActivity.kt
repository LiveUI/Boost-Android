package io.liveui.boost.ui.login

import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.intro.ChooseServerFragment
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import javax.inject.Inject

class LoginActivity : BoostActivity() {

    @Inject
    @ActivityScope
    lateinit var mainNavigator: MainNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mainNavigator.fragmentManager = supportFragmentManager
        mainNavigator.containerId = R.id.fragment_container

        mainNavigator.replaceFragment(FragmentNavigationItem(ChooseServerFragment::class.java))
    }
}
