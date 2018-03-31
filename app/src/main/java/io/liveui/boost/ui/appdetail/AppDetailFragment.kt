package io.liveui.boost.ui.appdetail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.common.EXTRA_APP_ID

import io.liveui.boost.R
import io.liveui.boost.api.ApiViewModeFactory
import io.liveui.boost.api.model.App
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.util.ProgressViewObserver
import kotlinx.android.synthetic.main.fragment_app_detail.*
import javax.inject.Inject


/**
 *
 */
class AppDetailFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var appDetailViewModel: AppDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_app_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appDetailViewModel = ViewModelProviders.of(this, apiViewModelFactory).get(AppDetailViewModel::class.java)
        appDetailViewModel.loadingStatus.observe(this, ProgressViewObserver(progressBar))
        appDetailViewModel.loadingStatus.observe(this, ProgressViewObserver(content_group, false))
        appDetailViewModel.app.observe(this, Observer<App> {
            app_name?.text = it?.name
            app_package_name?.text = it?.identifier
            app_platform?.text = getString(R.string.app_detail_platform, it?.platform)
            app_version?.text = getString(R.string.app_detail_version, it?.version)
        })
        appDetailViewModel.getApp(arguments?.getString(EXTRA_APP_ID) ?: "")
        btn_install.setOnClickListener({})
    }

}
