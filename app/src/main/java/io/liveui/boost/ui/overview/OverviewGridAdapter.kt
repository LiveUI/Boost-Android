package io.liveui.boost.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.download.DownloadStatus
import io.liveui.boost.ui.apps.BaseAppViewModel
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_overview_grid.view.*
import javax.inject.Inject
import javax.inject.Provider

class OverviewGridAdapter @Inject constructor(val baseAppViewModelProvider: Provider<OverviewAppItemViewModel>) : BaseObservableAdapter<AppOverview, OverviewGridViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewGridViewHolder {
        return OverviewGridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_overview_grid, parent, false), this, baseAppViewModelProvider.get())
    }

    override fun onBindViewHolder(holder: OverviewGridViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

}

class OverviewGridViewHolder(itemView: View, onItemClickListener: OnItemClickListener?, val baseAppViewModel: OverviewAppItemViewModel) : BaseViewHolder<AppOverview>(itemView, onItemClickListener) {

    init {
        itemView.btn_download.setOnClickListener(this)
    }

    override fun setData(item: AppOverview) {
        baseAppViewModel.app = item
        itemView.app_name.text = item.latest_app_name
        itemView.app_version.text = item.latest_app_version
        baseAppViewModel.loadAppIcon(itemView.app_icon, item.latest_app_id)

        baseAppViewModel.downloadStatus.observeForever {
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
                baseAppViewModel.downloadApp()
            }
            else -> {
                baseAppViewModel.openAppList()
            }
        }
    }
}