package io.liveui.boost.ui.intro


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.liveui.boost.BuildConfig
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.CheckViewModelFactory
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.ui.register.RegisterFragment
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.getString
import io.liveui.boost.util.ext.showSnackBar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.fragment_intro.*
import javax.inject.Inject
import javax.inject.Named

class ChooseServerFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: CheckViewModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    @Inject
    lateinit var userSession: UserSession

    @field:[Inject ActivityScope Named(MAIN_NAVIGATOR)]
    lateinit var mainNavigator: MainNavigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        server_tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                custom_server_url.visibility = if (TAB_CUSTOM.equals(tab?.tag) ) View.VISIBLE else View.GONE
            }
        })
        server_tabs.addTab(server_tabs.newTab().setText(R.string.client_name).setTag(TAB_LOCAL), true)
        server_tabs.addTab(server_tabs.newTab().setText(R.string.common_custom).setTag(TAB_CUSTOM), false)

        workspaceAddViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceAddViewModel::class.java)
        workspaceAddViewModel.serverExists.observe(this, Observer {
            if (it == true) {
                mainNavigator.replaceFragment(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true))
            } else {
                view.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)
            }
        })

        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(server_tabs, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(custom_server_url, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_login, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(line_or_left, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(or, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(line_or_right, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_register, false))

        custom_server_url.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, BuildConfig.URL))

        btn_login.setOnClickListener {
            userSession.workspace.url = custom_server_url.getString()
            workspaceAddViewModel.checkServer(userSession.workspace)
        }

        btn_register.setOnClickListener {
            mainNavigator.replaceFragment(FragmentNavigationItem(clazz = RegisterFragment::class.java, addToBackStack = true))
        }
    }

    companion object {
        const val TAB_LOCAL = "local"
        const val TAB_CUSTOM = "custom"
    }

}
