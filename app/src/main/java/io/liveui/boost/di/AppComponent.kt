package io.liveui.boost.di

import android.app.Application
import io.liveui.boost.AppStoreApplication
import dagger.android.AndroidInjector
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.liveui.boost.util.glide.GlideComponent
import io.liveui.boost.util.glide.HeaderGlideModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    InterceptorModule::class,
    NetworkModule::class,
    UtilModule::class,
    DataModule::class])
interface AppComponent : AndroidInjector<AppStoreApplication> {

    fun glideComponentBuilder(): GlideComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}