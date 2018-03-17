package io.liveui.boost.ui.apps

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.api.model.App
import kotlin.collections.ArrayList
import io.liveui.boost.R
import kotlinx.android.synthetic.main.view_holder_app.view.*

class AppsAdapter: RecyclerView.Adapter<AppsViewHolder>(), Observer<MutableList<App>> {

    val apps: MutableList<App> = ArrayList()

    override fun onChanged(t: MutableList<App>?) {
        apps.clear()
        if(t != null) {
            apps.addAll(t.asIterable())
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        return AppsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_app, parent, false))
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
       holder.setData(apps[position])
    }

}

class AppsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun setData(app: App) {
        itemView.app_name.text = app.name
        itemView.app_version.text = app.version
    }
}