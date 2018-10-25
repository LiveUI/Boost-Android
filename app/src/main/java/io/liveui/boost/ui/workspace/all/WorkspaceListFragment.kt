package io.liveui.boost.ui.workspace.all

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import kotlinx.android.synthetic.main.fragment_workspace_list.*
import javax.inject.Inject

class WorkspaceListFragment : BoostFragment() {

    @Inject
    lateinit var workspaceModelFactory: WorkspaceModelFactory

    lateinit var workspaceViewModel: WorkspaceListViewModel

    @Inject
    lateinit var workspaceListAdapter: WorkspaceListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workspace_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workspaceViewModel = ViewModelProviders.of(this, workspaceModelFactory).get(WorkspaceListViewModel::class.java)
        workspaceViewModel.loadWorkspace().observe(this, workspaceListAdapter)
        recycler_view.adapter = workspaceListAdapter
        recycler_view.layoutManager = if (resources.getBoolean(R.bool.isPhone)) LinearLayoutManager(context) else GridLayoutManager(context, 3)
        recycler_view.addDisposable(
                workspaceListAdapter.subject.subscribe {
                    when (it?.status) {
                        Workspace.Status.NEW -> {
                            startActivity(Intent(context, WorkspaceAddActivity::class.java))
                        }
                        else -> {
                            if(it?.active == 0) {
                                workspaceViewModel.changeWorkspace(it).run {
                                    startActivity(Intent(context, SplashActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK })
                                }
                            }
                        }
                    }
                }
        )
    }
}