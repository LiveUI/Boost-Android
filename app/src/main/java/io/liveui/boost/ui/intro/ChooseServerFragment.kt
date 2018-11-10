package io.liveui.boost.ui.intro


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.ui.register.RegisterFragment
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.setTabSelectedListener
import io.liveui.boost.util.ext.showSnackBar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.fragment_intro.*
import javax.inject.Inject
import javax.inject.Named

class ChooseServerFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: WorkspaceModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    @field:[Inject ActivityScope Named(MAIN_NAVIGATOR)]
    lateinit var mainNavigator: MainNavigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workspaceAddViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceAddViewModel::class.java)

        registerLoadingViews()

        server_tabs.addTab(server_tabs.newTab().setText(R.string.client_name).setTag(TAB_LOCAL), true)
        server_tabs.addTab(server_tabs.newTab().setText(R.string.common_custom).setTag(TAB_CUSTOM), false)
        server_tabs.setTabSelectedListener(onSelected = { tab ->
            when (tab.tag) {
                TAB_LOCAL -> {
                    workspaceAddViewModel.onClientServerSelected()
                }
                TAB_CUSTOM -> {
                    workspaceAddViewModel.onCustomServerSelected()
                }
            }
        })

        val urlAdapter = ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line)
        custom_server_url.setAdapter(urlAdapter)

        workspaceAddViewModel.suggestedUrl.observe(this, Observer {
            urlAdapter.addAll(it.toList())
        })

        btn_login.setOnClickListener {
            workspaceAddViewModel.onLoginClicked()
        }

        btn_register.setOnClickListener {
            mainNavigator.replaceFragment(FragmentNavigationItem(clazz = RegisterFragment::class.java, addToBackStack = true))
        }

        workspaceAddViewModel.customUrlTextFieldVisibility.observe(this, Observer{
            custom_server_url.visibility = if(it) View.VISIBLE else View.GONE
        })

        custom_server_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                workspaceAddViewModel.customServerUrl.postValue(custom_server_url.text.toString())
            }

        })

        workspaceAddViewModel.serverExists.observe(this, Observer {
            if (it == true) {
                mainNavigator.replaceFragment(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true))
            } else {
                view.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)
            }
        })
    }

    fun registerLoadingViews() {
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(server_tabs, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(custom_server_url, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_login, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(line_or_left, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(or, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(line_or_right, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_register, false))
    }

    companion object {
        const val TAB_LOCAL = "local"
        const val TAB_CUSTOM = "custom"
    }

}
