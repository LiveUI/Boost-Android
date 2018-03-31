package io.liveui.boost.ui.apps

import android.arch.lifecycle.MutableLiveData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.api.model.App
import io.liveui.boost.R
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_app.view.*

class AppsAdapter: BaseObservableAdapter<App, AppsViewHolder>() {

    val downloadItem: MutableLiveData<App> = MutableLiveData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        return AppsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_app, parent, false), this)
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
       holder.setData(items[position])
    }
}

class AppsViewHolder(itemView: View, onClickListener: OnItemClickListener?) : BaseViewHolder<App>(itemView, onClickListener) {

    override fun setData(item: App) {
        itemView.app_name.text = item.name
        itemView.app_version.text = item.version
    }

}