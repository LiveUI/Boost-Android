package cz.mangoweb.appstore.di

import cz.mangoweb.appstore.ui.apps.AppStoreActivity
import cz.mangoweb.appstore.ui.login.LoginActivity
import cz.mangoweb.appstore.ui.teams.TeamsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun provideLoginActivity(): LoginActivity


    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideAppsActivity(): AppStoreActivity


    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideTeamsActivity(): TeamsActivity
}