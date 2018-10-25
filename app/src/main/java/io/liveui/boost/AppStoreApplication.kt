package io.liveui.boost

import android.content.Context
import androidx.multidex.MultiDex
import io.liveui.boost.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.liveui.boost.di.AppComponent
import io.liveui.boost.util.permission.PermissionHelper
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber
import javax.inject.Inject

class AppStoreApplication : DaggerApplication() {

    @Inject
    lateinit var permissionHelper: PermissionHelper

    companion object {
        lateinit var component: AppComponent
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        Timber.plant(Timber.DebugTree())
        RxJavaPlugins.setErrorHandler {
            Timber.d(it)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerAppComponent.builder()
                .application(this)
                .build()
        return component
    }

    override fun onCreate() {
        super.onCreate()
        permissionHelper.start()
    }
}