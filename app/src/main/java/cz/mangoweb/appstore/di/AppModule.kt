package cz.mangoweb.appstore.di

import android.content.Context
import cz.mangoweb.appstore.AppStoreApplication
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun bindContext(application: AppStoreApplication): Context

}