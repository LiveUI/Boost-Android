package io.liveui.boost.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.model.SettingsItem
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_settings.view.*

class SettingsAdapter: BaseObservableAdapter<SettingsItem, SettingsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_settings, parent, false), this)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
       holder.setData(items[position])
    }
}

class SettingsViewHolder(itemView: View, onClickListener: OnItemClickListener?) : BaseViewHolder<SettingsItem>(itemView, onClickListener) {

    override fun setData(item: SettingsItem) {
        itemView.text1.text = item.name
    }

}