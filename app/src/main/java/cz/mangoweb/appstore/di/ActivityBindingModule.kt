package cz.mangoweb.appstore.di

import cz.mangoweb.appstore.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun provideLoginActivity(): LoginActivity
}