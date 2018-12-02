package io.liveui.boost.ui.workspace.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.liveui.boost.BuildConfig
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.databinding.FragmentWorkspaceBinding
import io.liveui.boost.ui.*
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.showSnackBar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import kotlinx.android.synthetic.main.fragment_workspace.*
import javax.inject.Inject

class WorkspaceAddFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: WorkspaceModelFactory

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    lateinit var navigationViewModel: NavigationViewModel

    private lateinit var uiActivityViewModel: UiActivityViewModel

    private lateinit var toolbarViewModel: ToolbarViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.let {
            navigationViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(NavigationViewModel::class.java)
            toolbarViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(ToolbarViewModel::class.java)
            uiActivityViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(UiActivityViewModel::class.java)
        }
        workspaceAddViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceAddViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentWorkspaceBinding>(inflater, R.layout.fragment_workspace, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = workspaceAddViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiActivityViewModel.background.postValue(android.R.color.transparent)
        toolbarViewModel.title.postValue(getString(R.string.workspace_add_title))
        toolbarViewModel.show.postValue(View.VISIBLE)
        workspaceAddViewModel.onCustomServerSelected()
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(til_workspace_url, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_continue, false))

        workspace_url.setAdapter(ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line, BuildConfig.URL))

        btn_continue.setOnClickListener { goToLogin() }

        workspaceAddViewModel.uiState.observe(this, Observer {
            when (it) {
                is UiState.Success -> changeFragment(it.navigationItem)
                is UiState.Error -> {
                    showError()
                    activity?.finish()
                }
            }
        })
    }

    private fun goToLogin() = workspaceAddViewModel.onAddServerClick()

    private fun showError() = view?.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)

    private fun changeFragment(navigationItem: FragmentNavigationItem) = navigationViewModel.mainNavigator.replaceFragment(navigationItem)

}