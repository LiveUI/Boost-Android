package io.liveui.boost.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.liveui.boost.AppStoreApplication
import io.liveui.boost.util.glide.GlideComponent
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    InterceptorModule::class,
    NetworkModule::class,
    UtilModule::class,
    DataModule::class,
    ActivityNavigationModule::class])
interface AppComponent : AndroidInjector<AppStoreApplication> {

    fun glideComponentBuilder(): GlideComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}