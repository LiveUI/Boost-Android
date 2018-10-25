package io.liveui.boost.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.download.DownloadStatus
import io.liveui.boost.ui.apps.AppsItemViewModel
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_overview_grid.view.*
import javax.inject.Inject
import javax.inject.Provider

class OverviewAdapter @Inject constructor(val appsItemViewModelProvider: Provider<AppsItemViewModel>) : BaseObservableAdapter<AppOverview, OverviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        return OverviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_overview_grid, parent, false), this, appsItemViewModelProvider.get())
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

}

class OverviewViewHolder(itemView: View, onItemClickListener: OnItemClickListener?, val appsItemViewModel: AppsItemViewModel) : BaseViewHolder<AppOverview>(itemView, onItemClickListener) {

    init {
        itemView.btn_download.setOnClickListener(this)
    }

    override fun setData(item: AppOverview) {
        appsItemViewModel.app = item
        itemView.app_name.text = item.latest_app_name
        itemView.app_version.text = item.latest_app_version
        appsItemViewModel.loadAppIcon(itemView.app_icon, item.latest_app_id)

        appsItemViewModel.downloadStatus.observeForever {
            when {
                it == DownloadStatus.COMPLETED -> {
                    itemView.btn_download.visibility = View.VISIBLE
                    itemView.download_progress_bar.visibility = View.INVISIBLE
                }
                arrayListOf(DownloadStatus.IN_PROGRESS, DownloadStatus.TOKEN_VERIFICATION_STARTED, DownloadStatus.TOKEN_VERIFICATION_COMPLETE).contains(it) -> {
                    itemView.download_progress_bar.visibility = View.VISIBLE
                    itemView.btn_download.visibility = View.INVISIBLE
                }
                else -> {
                    itemView.download_progress_bar.visibility = View.INVISIBLE
                    itemView.btn_download.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_download -> {
                appsItemViewModel.downloadApp()
            }
            else -> {
                super.onClick(v)
            }
        }
    }
}