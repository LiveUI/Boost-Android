package cz.mangoweb.appstore

import cz.mangoweb.appstore.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppStoreApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerAppComponent.builder()
                .application(this)
                .build()
        component.inject(this)
        return component
    }

}