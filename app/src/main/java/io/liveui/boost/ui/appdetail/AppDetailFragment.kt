package io.liveui.boost.ui.appdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.common.EXTRA_APP_ID

import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
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

        appDetailViewModel.appName.observe(this, Observer {
            app_name?.text = it
        })

        appDetailViewModel.appIdentifier.observe(this, Observer {
            app_package_name?.text = it
        })

        appDetailViewModel.appVersion.observe(this, Observer {
            app_version?.text = it
        })

        appDetailViewModel.id.observe(this, Observer {
            appDetailViewModel.loadAppIcon(app_logo, it)
        })

        appDetailViewModel.getApp(arguments?.getString(EXTRA_APP_ID) ?: "")

        btn_install.setOnClickListener {
            appDetailViewModel.downloadApp()
        }
        btn_settings.setOnClickListener {
            appDetailViewModel.openSettings()
        }
        btn_open.setOnClickListener {
            appDetailViewModel.openApp()
        }

        appDetailViewModel.isApkDownloadIdle.observe(this, Observer {
            group_app_not_downloaded.visibility = if(it) ConstraintLayout.VISIBLE else ConstraintLayout.GONE
        })

        appDetailViewModel.isAppDownloadInProgress.observe(this, Observer {
            group_app_downloaded_in_progress.visibility = if(it) ConstraintLayout.VISIBLE else ConstraintLayout.GONE
        })

        appDetailViewModel.isAppDownloaded.observe(this, Observer {
            group_app_downloaded.visibility = if(it) ConstraintLayout.VISIBLE else ConstraintLayout.GONE
        })
    }

}
