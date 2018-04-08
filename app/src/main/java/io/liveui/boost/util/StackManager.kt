package io.liveui.boost.util

import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import java.lang.ref.WeakReference

class StackManager(appCompatActivity: AppCompatActivity) : FragmentManager.OnBackStackChangedListener {

    val activityReference: WeakReference<AppCompatActivity> = WeakReference(appCompatActivity)
    val fragmentManager: FragmentManager?

    init {
        fragmentManager = getActivity()?.supportFragmentManager
        fragmentManager?.addOnBackStackChangedListener(this)
    }

    fun getActivity(): AppCompatActivity? {
        return activityReference.get()
    }

    fun onBackPressed() {
        getActivity()?.onBackPressed()
    }

    override fun onBackStackChanged() {

    }
}