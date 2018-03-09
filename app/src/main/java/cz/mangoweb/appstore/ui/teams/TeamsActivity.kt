package cz.mangoweb.appstore.ui.teams

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.ApiViewModeFactory
import cz.mangoweb.appstore.api.AuthViewModelFactory
import cz.mangoweb.appstore.ui.BoostActivity
import cz.mangoweb.appstore.ui.apps.AppsViewModel
import dagger.android.support.DaggerAppCompatActivity
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