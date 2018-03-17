package io.liveui.boost.util.ext

/**
 * Various extension functions for AppCompatActivity.
 */

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


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

fun View.hideKeyboard(view: View?) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}