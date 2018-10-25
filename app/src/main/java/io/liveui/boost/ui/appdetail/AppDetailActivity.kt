package io.liveui.boost.ui.appdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.liveui.boost.common.EXTRA_APP_ID
import io.liveui.boost.R
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.activity_app_detail.*
import javax.inject.Inject
import javax.inject.Named

class AppDetailActivity : BoostActivity() {

    @Inject
    @ActivityScope
    lateinit var mainNavigator: MainNavigator

    companion object {
        fun startActivity(context: Context?, appId: String?) {
            val intent = Intent(context, AppDetailActivity::class.java)
            intent.putExtra(EXTRA_APP_ID, appId)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail)

        mainNavigator.fragmentManager = supportFragmentManager
        mainNavigator.containerId = R.id.fragment_container

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mainNavigator.replaceFragment(FragmentNavigationItem(clazz = AppDetailFragment::class.java, args = intent.extras))
    }
}
