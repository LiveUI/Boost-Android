package io.liveui.boost.util.ext

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import java.io.Serializable

fun Fragment.putString(key: String, value: String) {
    val arg: Bundle = arguments ?: Bundle()
    arg.putString(key, value)
    arguments = arg
}

fun Fragment.putInt(key: String, value: Int) {
    val arg: Bundle = arguments ?: Bundle()
    arg.putInt(key, value)
    arguments = arg
}

fun Fragment.putFloat(key: String, value: Float) {
    val arg: Bundle = arguments ?: Bundle()
    arg.putFloat(key, value)
    arguments = arg
}

fun Fragment.putSerializable(key: String, value: Serializable) {
    val arg: Bundle = arguments ?: Bundle()
    arg.putSerializable(key, value)
    arguments = arg
}

fun Fragment.putParcelable(key: String, value: Parcelable) {
    val arg: Bundle = arguments ?: Bundle()
    arg.putParcelable(key, value)
    arguments = arg
}

fun Fragment.putIntentExtras(intent: Intent) {
    val arg: Bundle = arguments ?: Bundle()
    if (intent.extras != null) {
        arg.putAll(intent.extras)
    }
    arguments = arg
}

fun Fragment.replaceChildFragment(fragment: Fragment, frameId: Int): Fragment {
    childFragmentManager.transact {
        replace(frameId, fragment)
    }
    return fragment
}

