package cz.mangoweb.appstore.di

import cz.mangoweb.appstore.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule() {

    @ContributesAndroidInjector
    abstract fun provideLoginActivity(): LoginActivity
}