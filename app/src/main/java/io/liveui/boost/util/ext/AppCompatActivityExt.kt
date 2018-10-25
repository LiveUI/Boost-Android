package io.liveui.boost.util.ext

/**
 * Various extension functions for AppCompatActivity.
 */

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import io.liveui.boost.util.navigation.FragmentNavigationItem


inline fun FragmentManager.transactWithNavigationItem(navigationItem: FragmentNavigationItem, action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        navigationItem.transitionOptions?.sharedViews?.forEach {
            val sharedView = it.first.get()
            if (sharedView != null) {
                val transitionName: String? = ViewCompat.getTransitionName(sharedView)
                if (transitionName != null) {
                    addSharedElement(sharedView, transitionName)
                }
            }
        }
        if (navigationItem.addToBackStack) {
            addToBackStack(navigationItem.backStackName)
        }
        setReorderingAllowed(navigationItem.reorderingAllowed)
        action()
    }.commit()
}

fun <T : View> AppCompatActivity.setupView(resId: Int, action: T.() -> Unit) {
    findViewById<T>(resId)?.apply {
        action()
    }
}

fun <T> setupView(view: T?, action: T.() -> Unit) {
    view?.apply {
        action()
    }
}
