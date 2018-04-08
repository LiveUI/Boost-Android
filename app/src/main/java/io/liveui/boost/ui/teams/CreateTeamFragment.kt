package io.liveui.boost.ui.teams

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.ApiViewModeFactory
import io.liveui.boost.api.model.CreateTeamRequest
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.util.ext.getString
import io.liveui.boost.util.ext.showSnackBar
import kotlinx.android.synthetic.main.fragment_create_team.*
import javax.inject.Inject

class CreateTeamFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var teamsViewModel: TeamsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(TeamsViewModel::class.java)
        btn_create.setOnClickListener({
            teamsViewModel.createTeam(CreateTeamRequest(team_name.getString(), team_identifier.getString()))
        })

        teamsViewModel.teamCreate.observe(this, Observer {
            activity?.findViewById<View>(android.R.id.content)?.showSnackBar(R.string.team_create_success, Snackbar.LENGTH_SHORT)
        })

    }
}