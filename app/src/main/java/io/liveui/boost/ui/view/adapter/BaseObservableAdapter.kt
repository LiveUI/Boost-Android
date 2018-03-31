package io.liveui.boost.ui.view.adapter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseObservableAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>(), Observer<MutableList<T>>, OnItemClickListener {

    val items: MutableList<T> = ArrayList()
    val selectedItem: MutableLiveData<T> = MutableLiveData()

    override fun onChanged(t: MutableList<T>?) {
        items.clear()
        if (t != null) {
            items.addAll(t.asIterable())
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    override fun onItemClick(view: View?, position: Int) {
        selectedItem.value = items[position]
    }

}

