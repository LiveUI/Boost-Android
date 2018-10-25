package io.liveui.boost.ui.teams


import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import kotlinx.android.synthetic.main.fragment_teams.*
import javax.inject.Inject


/**
 *
 */
class TeamsFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var teamsViewModel: TeamsViewModel

    @Inject
    lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsViewModel = ViewModelProviders.of(activity!!, apiViewModelFactory).get(TeamsViewModel::class.java)
        teamsViewModel.teams.observe(this, teamsAdapter)
        recycler_view.adapter = teamsAdapter
        recycler_view.layoutManager = if (resources.getBoolean(R.bool.isPhone)) LinearLayoutManager(context) else GridLayoutManager(context, 3)
        recycler_view.addDisposable(
                teamsAdapter.subject.subscribe {
                    teamsViewModel.activeTeam.value = it
                }
        )
        teamsViewModel.loadTeams()
    }

}
