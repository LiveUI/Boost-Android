package io.liveui.boost.util

import androidx.lifecycle.Observer
import android.view.View
import io.liveui.boost.util.ext.toggleVisibilityWithFade

class ProgressViewObserver constructor(val view: View?, var showOnProgress: Boolean = true): Observer<Boolean> {

    init {
        view?.visibility = if(showOnProgress) View.GONE else View.VISIBLE
    }

    override fun onChanged(t: Boolean?) {
        view?.toggleVisibilityWithFade(t == showOnProgress)
    }

}