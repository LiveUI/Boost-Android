package io.liveui.boost.util.ext

/**
 * Various extension functions for AppCompatActivity.
 */

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View


fun View.toggleVisibilityWithFade(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
    animate()
            .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibility = if (show) View.VISIBLE else View.GONE
                }
            })

}