package io.liveui.boost.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadIcon")
fun loadIcon(view: ImageView, url: String?) {
    url?.let {
        Glide.with(view).load(it).into(view)
    }
}
