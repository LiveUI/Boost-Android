package io.liveui.boost.ui.workspace.add

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.liveui.boost.BuildConfig
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.CheckViewModelFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.*
import kotlinx.android.synthetic.main.fragment_workspace.*
import javax.inject.Inject

class WorkspaceAddFragment : BoostFragment() {

    @Inject
    lateinit var checkViewModelFactory: CheckViewModelFactory

    lateinit var workspaceAddViewModel: WorkspaceAddViewModel

    @Inject
    lateinit var userSession: UserSession

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workspace, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workspaceAddViewModel = ViewModelProviders.of(this, checkViewModelFactory).get(WorkspaceAddViewModel::class.java)
        workspaceAddViewModel.serverExists.observe(this, Observer {
            if (it!!) {
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                view.showSnackBar("Server doesn't exists", Snackbar.LENGTH_SHORT)
            }
        })

        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(til_workspace_url, false))
        workspaceAddViewModel.loadingStatus.observe(this, ProgressViewObserver(btn_continue, false))

        workspace_url.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, BuildConfig.URL))

        btn_continue.setOnClickListener {
            userSession.workspace.url = workspace_url.getString()
            workspaceAddViewModel.checkServer(userSession.workspace)
        }

    }
}