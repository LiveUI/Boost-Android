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

class OverviewAdapter : BaseObservableAdapter<AppOverview, OverviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewViewHolder {
        return OverviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_overview, parent, false), this)
    }

    override fun onBindViewHolder(holder: OverviewViewHolder, position: Int) {
        holder.setData(getItem(position))
    }


}

class OverviewViewHolder(itemView: View, onItemClickListener: OnItemClickListener?) : BaseViewHolder<AppOverview>(itemView, onItemClickListener) {

    /**
     * "latest_name": "GoldCoast",
    "count": 121,
    "latest_build": "2321",
    "latest_version": "1.10.10",
    "identifier": "io.liveui.goldcoast",
    "platform": "android"
     */
    override fun setData(item: AppOverview) {
        itemView.app_name.text = item.latest_name
        itemView.app_identifier.text = item.identifier
        itemView.app_version.text = item.latest_version
    }

}