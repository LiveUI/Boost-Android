package io.liveui.boost.ui.workspace.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.liveui.boost.BuildConfig
import io.liveui.boost.EXTRA_WORKSPACE
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.databinding.FragmentWorkspaceBinding
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.showSnackBar
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.fragment_workspace.*
import javax.inject.Inject

class WorkspaceAddFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: WorkspaceModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    lateinit var mainNavigator: MainNavigator

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mainNavigator = (context as WorkspaceAddActivity).mainNavigator //TODO fix - create navigator view model
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentWorkspaceBinding>(inflater, R.layout.fragment_workspace, container, false)
        binding.setLifecycleOwner(this)
        workspaceAddViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceAddViewModel::class.java)
        binding.viewModel = workspaceAddViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workspaceAddViewModel.onCustomServerSelected()
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(til_workspace_url, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_continue, false))

        workspace_url.setAdapter(ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line, BuildConfig.URL))

        btn_continue.setOnClickListener { goToLogin() }
    }

    fun goToLogin() {
        workspaceAddViewModel.verifyServer(onServerVerified = {
            mainNavigator.replaceFragment(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true, args = Bundle().apply {
                putParcelable(EXTRA_WORKSPACE, it)
            }))
        }, onServerVerifyError = {
            view?.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)
        })
    }
}