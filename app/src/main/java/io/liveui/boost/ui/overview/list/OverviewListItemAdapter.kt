package io.liveui.boost.ui.overview.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.App
import io.liveui.boost.download.DownloadStatus
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_overview_list_app.view.*
import javax.inject.Inject
import javax.inject.Provider

class OverviewListItemAdapter @Inject constructor(private val overviewListItemViewModelProvider: Provider<OverviewListAppItemViewModel>)
    : BaseObservableAdapter<App, OverviewListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewListItemViewHolder {
        return OverviewListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_overview_list_app, parent, false),
                this,
                overviewListItemViewModelProvider.get())
    }

    override fun onBindViewHolder(holder: OverviewListItemViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

}

class OverviewListItemViewHolder(itemView: View, onItemClickListener: OnItemClickListener?,
                                 private val overviewListItemViewModel: OverviewListAppItemViewModel)
    : BaseViewHolder<App>(itemView, onItemClickListener) {

    init {
        itemView.btn_download.setOnClickListener(this)
    }

    override fun setData(item: App) {
        overviewListItemViewModel.app = item
        itemView.app_version.text = "Version: ${item.getAppVersion()}" //TODO remove hardcoded strings
        itemView.app_build.text = "Build: ${item.getAppBuild()}"
        overviewListItemViewModel.downloadStatus.observeForever {
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
                overviewListItemViewModel.downloadApp()
            }
            else -> {
                overviewListItemViewModel.openAppDetail()
            }
        }
    }

}