package io.liveui.boost

import android.content.Context
import android.support.multidex.MultiDex
import io.liveui.boost.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.liveui.boost.util.permission.PermissionHelper
import timber.log.Timber
import javax.inject.Inject

class AppStoreApplication : DaggerApplication() {

    @Inject
    lateinit var permissionHelper: PermissionHelper

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        permissionHelper.start()
    }
}