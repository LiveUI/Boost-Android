package io.liveui.boost.ui.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import io.liveui.boost.R
import io.liveui.boost.api.model.AppOverview
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_overview.view.*
import javax.inject.Inject
import javax.inject.Provider

class OverviewListAdapter @Inject constructor(val baseGridViewModelProvider: Provider<OverviewListItemViewModel>) : BaseObservableAdapter<AppOverview, OverviewListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverviewListViewHolder {
        return OverviewListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_overview, parent, false), this, baseGridViewModelProvider.get(), baseGridViewModelProvider)
    }

    override fun onBindViewHolder(holder: OverviewListViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

}

class OverviewListViewHolder(itemView: View, onItemClickListener: OnItemClickListener?, val baseGridViewModel: OverviewListItemViewModel, baseGridViewModelProvider: Provider<OverviewListItemViewModel>) : BaseViewHolder<AppOverview>(itemView, onItemClickListener) {

    val adapter = OverviewListItemAdapter(baseGridViewModelProvider)

    init {
        baseGridViewModel.latestBuilds.observeForever(adapter)
    }

    override fun setData(item: AppOverview) {
        baseGridViewModel.app = item
        itemView.app_name.text = item.latest_app_name
        itemView.builds_count.text = "${item.build_count} builds"
        itemView.app_identifier.text = item.identifier
        baseGridViewModel.loadAppIcon(itemView.app_logo, item.latest_app_id)
        itemView.recycler_view_apps.layoutManager = LinearLayoutManager(itemView.context)
        itemView.recycler_view_apps.adapter = adapter
        itemView.recycler_view_apps.updateLayoutParams<ViewGroup.LayoutParams> {
            val maxRow = if (item.build_count >= 3) 3 else item.build_count
            height = maxRow * itemView.resources.getDimensionPixelSize(R.dimen.overview_list_item_height)
        }
    }

    override fun onClick(v: View) {
        baseGridViewModel.openAppList()
    }
}