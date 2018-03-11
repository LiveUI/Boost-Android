package cz.mangoweb.appstore.di

import cz.mangoweb.appstore.ui.appdetail.AppDetailFragment
import cz.mangoweb.appstore.ui.apps.AppStoreActivity
import cz.mangoweb.appstore.ui.apps.AppsFragment
import cz.mangoweb.appstore.ui.login.LoginActivity
import cz.mangoweb.appstore.ui.splash.SplashActivity
import cz.mangoweb.appstore.ui.teams.TeamsActivity
import cz.mangoweb.appstore.ui.teams.TeamsFragment
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

    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun proviedeAppsFragment(): AppsFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideAppDetailFragment(): AppDetailFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideTeamsFragment(): TeamsFragment

}