package cz.mangoweb.appstore.util

import android.arch.lifecycle.Observer
import android.view.View

class ProgressViewObserver constructor(val view: View?, var showOnProgress: Boolean = true): Observer<Boolean> {

    init {
        view?.visibility = if(showOnProgress) View.GONE else View.VISIBLE
    }

    override fun onChanged(t: Boolean?) {
        view?.toggleVisibilityWithFade(t == showOnProgress)
    }

}