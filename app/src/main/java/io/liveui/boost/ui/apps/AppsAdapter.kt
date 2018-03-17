package io.liveui.boost.ui.apps

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.api.model.App
import kotlin.collections.ArrayList
import io.liveui.boost.R
import kotlinx.android.synthetic.main.view_holder_app.view.*

class AppsAdapter: RecyclerView.Adapter<AppsViewHolder>(), Observer<MutableList<App>>, OnItemClickListener {

    val apps: MutableList<App> = ArrayList()
    val selectedItem: MutableLiveData<App> = MutableLiveData()
    val downloadItem: MutableLiveData<App> = MutableLiveData()

    override fun onChanged(t: MutableList<App>?) {
        apps.clear()
        if(t != null) {
            apps.addAll(t.asIterable())
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        return AppsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_app, parent, false), this)
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
       holder.setData(apps[position])
    }

    override fun onItemClick(view: View?, position: Int) {
        when(view?.id) {
            R.id.btn_download -> downloadItem.value = apps[position]
            else -> {
                selectedItem.value = apps[position]
            }
        }
    }

}

interface OnItemClickListener {
    fun onItemClick(view: View?, position: Int)
}

class AppsViewHolder(itemView: View?, val onClickListener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
        itemView?.setOnClickListener(this)
    }

    fun setData(app: App) {
        itemView.app_name.text = app.name
        itemView.app_version.text = app.version
    }

    override fun onClick(v: View?) {
        onClickListener?.onItemClick(v, adapterPosition)
    }

}