package io.liveui.boost.ui.apps


import android.Manifest
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
import io.liveui.boost.api.DownloadManager
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

    @Inject
    lateinit var downloadManager: DownloadManager

    @Inject
    lateinit var permissionHelper: PermissionHelper

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
        recycler_view.layoutManager = if(resources.getBoolean(R.bool.isPhone)) LinearLayoutManager(context) else GridLayoutManager(context, 3)
        appsAdapter.selectedItem.observe(this, Observer {
            AppDetailActivity.startActivity(context, it?.id)
        })

        appsAdapter.downloadItem.observe(this, Observer {
            if (permissionHelper.checkPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1)) {
                downloadManager.downloadApp(it!!.id)
            }
        })

        overviewViewModel.activeOverview.observe(this, Observer {
            appsViewModel.getFilteredApps(it?.identifier)
        })

    }
}
