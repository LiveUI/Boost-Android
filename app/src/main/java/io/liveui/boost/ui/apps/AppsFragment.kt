package io.liveui.boost.ui.apps


import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.liveui.boost.R
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.overview.OverviewViewModel
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.permission.PermissionHelper
import kotlinx.android.synthetic.main.fragment_apps.*
import javax.inject.Inject

/**
 *
 */
class AppsFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var appsViewModel: AppsViewModel

    lateinit var overviewViewModel: OverviewViewModel

    @Inject
    lateinit var appsAdapter: AppsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(AppsViewModel::class.java)
        overviewViewModel = ViewModelProviders.of(activity!!, apiViewModelFactory).get(OverviewViewModel::class.java)

        appsViewModel.loadingStatus.observe(this, ProgressViewObserver(progressBar))
        appsViewModel.loadingStatus.observe(this, ProgressViewObserver(recycler_view, false))
        appsViewModel.apps.observe(this, appsAdapter)
        recycler_view.adapter = appsAdapter
        recycler_view.layoutManager = if (resources.getBoolean(R.bool.isPhone)) LinearLayoutManager(context) else GridLayoutManager(context, 3)

        overviewViewModel.activeOverview.observe(this, Observer {
            appsViewModel.getFilteredApps(it?.identifier)
        })

    }
}
