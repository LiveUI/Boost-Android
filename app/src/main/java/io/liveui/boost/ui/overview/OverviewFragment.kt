package io.liveui.boost.ui.overview

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.liveui.boost.R
import io.liveui.boost.common.model.LayoutManagerConfig
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.apps.AppsFragment
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.ui.view.adapter.SpaceItemDecoration
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject

class OverviewFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var teamsViewModel: TeamsViewModel

    lateinit var overviewViewModel: OverviewViewModel

    @Inject
    lateinit var overviewAdapter: OverviewAdapter

    @Inject
    @ActivityScope
    lateinit var mainNavigator: MainNavigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsViewModel = ViewModelProviders.of(activity!!, apiViewModelFactory).get(TeamsViewModel::class.java)
        overviewViewModel = ViewModelProviders.of(activity!!, apiViewModelFactory).get(OverviewViewModel::class.java)
        teamsViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        teamsViewModel.loadingStatus.observe(this, ProgressViewObserver(recycler_view, false))

        overviewViewModel.overviewData.observe(this, overviewAdapter)

        recycler_view.adapter = overviewAdapter
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.overview_column_space)))

        btn_show_grid.setOnClickListener { overviewViewModel.showGrid() }
        btn_show_list.setOnClickListener { overviewViewModel.showList() }

        teamsViewModel.activeTeam.observe(this, Observer {
            if (it == null) {
                overviewViewModel.loadAppsOverview()
            } else {
                overviewViewModel.loadTeamAppsOverview(it.id)
            }
        })

        recycler_view.addDisposable(
                overviewAdapter.subject.subscribe {
                    overviewViewModel.activeOverview.value = it
                    mainNavigator.replaceFragment(FragmentNavigationItem(clazz = AppsFragment::class.java, addToBackStack = true))
                }
        )

        overviewViewModel.layoutType.observe(this, Observer {
            when (it) {
                LayoutManagerConfig.PHONE_LIST -> {
                    recycler_view.layoutManager = LinearLayoutManager(context)
                }
                LayoutManagerConfig.PHONE_GRID -> {
                    recycler_view.layoutManager = GridLayoutManager(context, 2)
                }
                LayoutManagerConfig.TABLET_LIST -> {
                }
                LayoutManagerConfig.TABLET_GRID -> {
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}