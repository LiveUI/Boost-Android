package io.liveui.boost.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_overview.view.*
import javax.inject.Inject
import javax.inject.Provider

class OverviewListAdapter @Inject constructor(val baseAppViewModelProvider: Provider<OverviewAppItemViewModel>) : BaseObservableAdapter<AppOverview, OverviewListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewListViewHolder {
        return OverviewListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_overview, parent, false), this, baseAppViewModelProvider.get())
    }

    override fun onBindViewHolder(holder: OverviewListViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

}

class OverviewListViewHolder(itemView: View, onItemClickListener: OnItemClickListener?, val baseAppViewModel: OverviewAppItemViewModel) : BaseViewHolder<AppOverview>(itemView, onItemClickListener) {

    override fun setData(item: AppOverview) {
        baseAppViewModel.app = item
        itemView.app_name.text = item.latest_app_name
        itemView.builds_count.text = "${item.build_count} builds"
        itemView.app_identifier.text = item.identifier
        baseAppViewModel.loadAppIcon(itemView.app_logo, item.latest_app_id)
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