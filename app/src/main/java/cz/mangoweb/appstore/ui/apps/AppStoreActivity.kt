package cz.mangoweb.appstore.ui.apps

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.AuthViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */
class AppStoreActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var apiViewModelFactory: AuthViewModelFactory

    lateinit var appsViewModel: AppsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        appsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(AppsViewModel::class.java)

    }
}