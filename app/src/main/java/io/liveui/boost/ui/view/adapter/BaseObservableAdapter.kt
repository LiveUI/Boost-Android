package io.liveui.boost.ui.view.adapter

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject

abstract class BaseObservableAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>(), Observer<MutableList<T>>, OnItemClickListener {

    val items: MutableList<T> = ArrayList()
    val subject: PublishSubject<T> = PublishSubject.create()

    override fun onChanged(newItems: MutableList<T>?) {
        addItems(newItems)
        notifyDataSetChanged()
    }

    open fun addItems(newItems: MutableList<T>?) {
        val diffUtil = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition]?.equals(newItems?.get(newItemPosition)) ?: false
            }

            override fun getOldListSize(): Int {
                return items.size
            }

            override fun getNewListSize(): Int {
                return newItems?.size ?: 0
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition]?.equals(newItems?.get(newItemPosition)) ?: false
            }

        })

        items.clear()
        if (newItems != null) {
            items.addAll(newItems.asIterable())
        }

        diffUtil.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    override fun onItemClick(view: View?, position: Int) {
        subject.onNext(getItem(position))
    }

}

