package io.liveui.boost.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.LoginModule
import io.liveui.boost.ui.MainActivity
import io.liveui.boost.ui.MainActivityModule
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.ui.settings.SettingsActivity
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.teams.TeamsActivity
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import io.liveui.boost.ui.workspace.all.WorkspaceListActivity

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun provideLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun provideMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun provideAppsActivity(): AppsActivity

    @ContributesAndroidInjector
    abstract fun provideTeamsActivity(): TeamsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun provideAppDetailsActivity(): AppDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class, MainActivityModule::class])
    abstract fun provideWorkspaceAddActivity(): WorkspaceAddActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginModule::class, MainActivityModule::class])
    abstract fun provideWorkspaceListActivity(): WorkspaceListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun provideSettingsActivity(): SettingsActivity
}