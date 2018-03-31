package io.liveui.boost.ui.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<T>(itemView: View, val onItemClickListener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
        if(onItemClickListener != null) {
            itemView.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        onItemClickListener?.onItemClick(v, adapterPosition)
    }

    abstract fun setData(item: T)
}