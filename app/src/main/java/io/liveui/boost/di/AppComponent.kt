package io.liveui.boost.di

import android.app.Application
import io.liveui.boost.AppStoreApplication
import dagger.android.AndroidInjector
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    InterceptorModule::class,
    NetworkModule::class,
    UtilModule::class,
    DownloadModule::class])
interface AppComponent : AndroidInjector<AppStoreApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}