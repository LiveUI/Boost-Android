package io.liveui.boost.ui.workspace.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_workspace.view.*

class WorkspaceListAdapter : BaseObservableAdapter<Workspace, WorkspaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        return WorkspaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_workspace, parent, false), this)
    }

    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun addItems(newItems: MutableList<Workspace>?) {
        var tempItems = newItems
        if (tempItems == null) {
            tempItems = ArrayList()
        }
        tempItems.add(Workspace(url = "").apply {
            active = 0
        })
        tempItems.sortedWith(compareBy(Workspace::status, Workspace::name))
        super.addItems(tempItems)
    }

}

class WorkspaceViewHolder(itemView: View, onClickListener: OnItemClickListener?) : BaseViewHolder<Workspace>(itemView, onClickListener) {

    override fun setData(item: Workspace) {
        itemView.indicator.isEnabled = item.active == 1
        when (item.status) {
            Workspace.Status.NEW -> {
                itemView.name.text = itemView.resources.getString(R.string.workspace_list_add_title)
                itemView.url.text = itemView.resources.getString(R.string.workspace_list_add_description)
                itemView.icon.setImageResource(R.drawable.ic_workspace_add)
            }
            else -> {
                itemView.name.text = item.name
                itemView.url.text = item.url
                itemView.icon.setImageBitmap(null)
            }
        }

    }

}