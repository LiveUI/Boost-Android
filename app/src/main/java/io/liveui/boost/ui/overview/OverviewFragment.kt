package io.liveui.boost.ui.overview

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.teams.TeamsViewModel
import io.liveui.boost.util.ProgressViewObserver
import kotlinx.android.synthetic.main.fragment_overview.*
import javax.inject.Inject

class OverviewFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var teamsViewModel: TeamsViewModel

    lateinit var overviewViewModel: OverviewViewModel

    @Inject
    lateinit var overviewAdapter: OverviewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsViewModel = ViewModelProviders.of(activity!!, apiViewModelFactory).get(TeamsViewModel::class.java)
        overviewViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(OverviewViewModel::class.java)
        teamsViewModel.loadingStatus.observe(this, ProgressViewObserver(progress_bar))
        teamsViewModel.loadingStatus.observe(this, ProgressViewObserver(recycler_view, false))

        overviewViewModel.overviewData.observe(this, overviewAdapter)
        recycler_view.adapter = overviewAdapter
        recycler_view.layoutManager = if (resources.getBoolean(R.bool.isPhone)) LinearLayoutManager(context) else GridLayoutManager(context, 3)

        teamsViewModel.activeTeam.observe(this, Observer {
            if (it == null) {
                overviewViewModel.loadAppsOverview()
            } else {
                overviewViewModel.loadTeamAppsOverview(it.id)
            }
        })

    }
}