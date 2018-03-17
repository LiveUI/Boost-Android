package io.liveui.boost.ui.teams

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.api.ApiViewModeFactory
import io.liveui.boost.ui.BoostActivity
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class TeamsActivity: BoostActivity() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var teamsViewModel: TeamsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        teamsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(TeamsViewModel::class.java)
        teamsViewModel.loadTeams()
    }
}