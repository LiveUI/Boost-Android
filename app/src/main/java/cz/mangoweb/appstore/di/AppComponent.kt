package cz.mangoweb.appstore.di

import android.app.Application
import cz.mangoweb.appstore.AppStoreApplication
import dagger.android.AndroidInjector
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),(ActivityBindingModule::class), (AppModule::class), (NetModule::class), (UtilModule::class)])
interface AppComponent: AndroidInjector<AppStoreApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}