package io.liveui.boost.ui.workspace.all

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        workspaceListAdapter.lifecycleOwner = this
        workspaceListAdapter.mainNavigator = (context as WorkspaceListActivity).mainNavigator //TODO fix - create navigator view model
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workspace_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workspaceViewModel = ViewModelProviders.of(this, workspaceModelFactory).get(WorkspaceListViewModel::class.java)
        workspaceViewModel.workspace.observe(this, workspaceListAdapter)
        recycler_view.adapter = workspaceListAdapter
        recycler_view.layoutManager = if (resources.getBoolean(R.bool.isPhone)) LinearLayoutManager(context) else GridLayoutManager(context, 3)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_workspace_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add -> {
                WorkspaceAddActivity.startActivity(context!!)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}