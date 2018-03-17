package io.liveui.boost.ui.apps


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.util.ProgressViewObserver
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

    @Inject
    lateinit var appsAdapter: AppsAdapter

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
        appsViewModel.apps.observe(this, appsAdapter)
        recycler_view.adapter = appsAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
    }
}
