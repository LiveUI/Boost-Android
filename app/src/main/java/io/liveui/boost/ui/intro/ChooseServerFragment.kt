package io.liveui.boost.ui.intro


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.databinding.FragmentIntroBinding
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.UiState
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.setTabSelectedListener
import io.liveui.boost.util.ext.showSnackBar
import io.liveui.boost.util.ext.toggleVisibilityWithFade
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.fragment_intro.*
import javax.inject.Inject

class ChooseServerFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: WorkspaceModelFactory

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    lateinit var navigationViewModel: NavigationViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.let {
            navigationViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(NavigationViewModel::class.java)
        }
        workspaceAddViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceAddViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentIntroBinding>(inflater, R.layout.fragment_intro, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = workspaceAddViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        btn_login.setOnClickListener { goToLogin() }
        btn_register.setOnClickListener { goToRegister() }

        workspaceAddViewModel.uiState.observe(this, Observer {
            when (it) {
                is UiState.Success -> changeFragment(it.navigationItem)
                is UiState.Error -> showError()
            }
        })
    }

    fun goToLogin() = workspaceAddViewModel.onLoginClick()

    fun goToRegister() = workspaceAddViewModel.onRegisterClick()

    fun registerLoadingViews() {
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(server_tabs, false))
        workspaceAddViewModel.loadingStatus.observe(this, Observer {
            if(custom_server_url.isVisible) {
                custom_server_url?.toggleVisibilityWithFade(false)
            }
        })
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_login, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(line_or_left, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(or, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(line_or_right, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_register, false))
    }

    private fun changeFragment(navigationItem: FragmentNavigationItem) = navigationViewModel.mainNavigator.replaceFragment(navigationItem)

    private fun showError() = view?.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)

    companion object {
        const val TAB_LOCAL = "local"
        const val TAB_CUSTOM = "custom"
    }

}
