package io.liveui.boost.ui.apps


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.apps.AppsActivity.Companion.EXTRA_APP
import io.liveui.boost.ui.overview.OverviewViewModel
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

    lateinit var toolbarVM: ToolbarViewModel

    @Inject
    lateinit var appsAdapter: AppsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appsViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(AppsViewModel::class.java)
        activity?.let {
            toolbarVM = ViewModelProviders.of(it, apiViewModelFactory).get(ToolbarViewModel::class.java)
        }
        arguments?.apply {
            appsViewModel.activeIdentifier.postValue(getString(EXTRA_APP))
        }
        appsViewModel.loadingStatus.observe(this, ProgressViewObserver(progressBar))
        appsViewModel.loadingStatus.observe(this, ProgressViewObserver(recycler_view, false))
        appsViewModel.apps.observe(this, appsAdapter)
        recycler_view.adapter = appsAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
    }
}
