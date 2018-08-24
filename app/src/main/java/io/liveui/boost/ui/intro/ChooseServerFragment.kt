package io.liveui.boost.ui.intro


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import io.liveui.boost.BuildConfig
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.CheckViewModelFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.getString
import io.liveui.boost.util.ext.showSnackBar
import kotlinx.android.synthetic.main.fragment_intro.*
import javax.inject.Inject

class ChooseServerFragment : BoostFragment() {

    companion object {
        const val TAB_LOCAL = "local"
        const val TAB_CUSTOM = "custom"
    }

    @Inject
    lateinit var checkViewModelFactory: CheckViewModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    @Inject
    lateinit var userSession: UserSession

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
                Navigation.findNavController(activity!!, R.id.login_nav_host_fragment).navigate(R.id.action_chooseServer_to_login)
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

        btn_login.setOnClickListener({
            userSession.workspace.url = custom_server_url.getString()
            workspaceAddViewModel.checkServer(userSession.workspace)
        })

        btn_register.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.login_nav_host_fragment).navigate(R.id.action_chooseServer_to_register)
        }
    }

}
