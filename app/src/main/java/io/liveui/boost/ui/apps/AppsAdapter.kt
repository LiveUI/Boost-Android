package io.liveui.boost.ui.apps

import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.api.model.App
import kotlin.collections.ArrayList

class AppsAdapter: RecyclerView.Adapter<AppsViewHolder>(), Observer<MutableList<App>> {

    val apps: MutableList<App> = ArrayList()

    override fun onChanged(t: MutableList<App>?) {
        apps.clear()
        if(t != null) {
            apps.addAll(t.asIterable())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class AppsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)