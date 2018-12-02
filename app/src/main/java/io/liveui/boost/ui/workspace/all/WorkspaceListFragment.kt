package io.liveui.boost.ui.workspace.all

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.UiActivityViewModel
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import kotlinx.android.synthetic.main.fragment_workspace_list.*
import javax.inject.Inject

class WorkspaceListFragment : BoostFragment() {

    @Inject
    lateinit var workspaceModelFactory: WorkspaceModelFactory

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    lateinit var workspaceViewModel: WorkspaceListViewModel

    lateinit var navigationViewModel: NavigationViewModel

    private lateinit var uiActivityViewModel: UiActivityViewModel

    private lateinit var toolbarViewModel: ToolbarViewModel

    @Inject
    lateinit var workspaceListAdapter: WorkspaceListAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.let {
            navigationViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(NavigationViewModel::class.java)
            toolbarViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(ToolbarViewModel::class.java)
            uiActivityViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(UiActivityViewModel::class.java)
        }
        workspaceViewModel = ViewModelProviders.of(this, workspaceModelFactory).get(WorkspaceListViewModel::class.java)

        workspaceListAdapter.lifecycleOwner = this
        workspaceListAdapter.mainNavigator = navigationViewModel.mainNavigator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workspace_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiActivityViewModel.background.postValue(android.R.color.transparent)
        toolbarViewModel.title.postValue(getString(R.string.workspace_list_title))
        toolbarViewModel.subtitle.postValue(getString(R.string.workspace_list_subtitle))
        toolbarViewModel.show.postValue(View.VISIBLE)
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