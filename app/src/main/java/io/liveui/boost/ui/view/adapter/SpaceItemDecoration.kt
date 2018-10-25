package io.liveui.boost.ui.view.adapter

import android.graphics.Rect
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class SpaceItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    var spanCount: Int = 1

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {

        spanCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1
        val position = parent.getChildLayoutPosition(view)

        when {
            position % spanCount == 0 -> {
                outRect.left = space
                outRect.right = space / 2
            }
            position.plus(1) % spanCount == 0 -> {
                outRect.left = space / 2
                outRect.right = space
            }
            else -> {
                outRect.left = space / 2
                outRect.right = space / 2
            }
        }

        outRect.bottom = space

        // Add top margin only for the first item to avoid double space between items
        if (position < spanCount) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}