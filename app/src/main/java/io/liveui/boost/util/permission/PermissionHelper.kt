package io.liveui.boost.util.permission

import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import io.liveui.boost.util.dispose
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

class PermissionHelper(val application: Application) : Application.ActivityLifecycleCallbacks, ActivityCompat.OnRequestPermissionsResultCallback {

    var activityRefrence: WeakReference<Activity?>? = null

    fun start() {
        application.registerActivityLifecycleCallbacks(this)
    }

    val disposable: CompositeDisposable = CompositeDisposable()

    fun isGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(application, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissions(permission: String, requestCode: Int): Boolean {
        return if(isGranted(permission)) {
            true
        } else {
            requestPermission(Array(1) {permission}, requestCode)
            false
        }
    }

    fun requestPermission(permissions: Array<out String>, requestCode: Int) {
        val activit: Activity? = activityRefrence?.get()
        if (activit != null) {
            ActivityCompat.requestPermissions(activit, permissions, requestCode) //TODO add Pair permission - request code
        }
    }

    override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
        activityRefrence = WeakReference(activity)
    }

    override fun onActivityDestroyed(activity: Activity?) {
        dispose(disposable)
        activityRefrence?.clear()
        activityRefrence = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }



}