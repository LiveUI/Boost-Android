package cz.mangoweb.appstore.ui.apps


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.ApiViewModeFactory
import cz.mangoweb.appstore.api.model.App
import cz.mangoweb.appstore.ui.BoostFragment
import cz.mangoweb.appstore.util.ProgressViewObserver
import kotlinx.android.synthetic.main.activity_apps.*
import kotlinx.android.synthetic.main.fragment_apps.*
import javax.inject.Inject

/**
 *
 */
class AppsFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var appsViewModel: AppsViewModel

    @Inject
    lateinit var appsConfig: AppsConfig

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(AppsViewModel::class.java)
        appsViewModel.loadingStatus.observe(this, ProgressViewObserver(progressBar))
        appsViewModel.loadingStatus.observe(this, ProgressViewObserver(recycler_view, false))
        appsViewModel.getApps(appsConfig.platform.value?.name?.toLowerCase()!!, appsConfig.identifier.value!!)
        appsViewModel.apps.observe(this, Observer<MutableList<App>> {

        })

    }
}
