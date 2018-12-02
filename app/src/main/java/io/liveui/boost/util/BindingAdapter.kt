package io.liveui.boost.util

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("viewBackground")
fun setViewBackground(view: View, resId: Int?) {
    resId?.let {
        view.setBackgroundResource(it)
    }
}