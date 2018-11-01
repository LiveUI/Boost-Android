package io.liveui.boost.util.ext

/**
 * Various extension functions for AppCompatActivity.
 */

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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

fun AppCompatActivity.setupToolbar(toolbar: Toolbar?, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        action()
    }
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

inline fun <reified T: ViewDataBinding> AppCompatActivity.setDatabindingContentView(@LayoutRes resId: Int, action: T.() -> Unit) {
    val dataBindingView = DataBindingUtil.setContentView<T>(this, resId).apply {
        action()
    }
    dataBindingView.setLifecycleOwner(this)
}