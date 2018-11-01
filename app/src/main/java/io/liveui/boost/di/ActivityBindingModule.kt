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
import io.liveui.boost.ui.apps.AppsModule
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.ui.settings.SettingsActivity
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.teams.TeamsActivity
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import io.liveui.boost.ui.workspace.all.WorkspaceListActivity

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [AuthModule::class, LoginModule::class, ActivityNavigationModule::class])
    abstract fun provideLoginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ApiModule::class, AppsModule::class, MainActivityModule::class, ActivityNavigationModule::class])
    abstract fun provideMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ApiModule::class, AppsModule::class, MainActivityModule::class, ActivityNavigationModule::class])
    abstract fun provideAppsActivity(): AppsActivity

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideTeamsActivity(): TeamsActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ApiModule::class, MainActivityModule::class, ActivityNavigationModule::class, ActivityNavigationModule::class])
    abstract fun provideAppDetailsActivity(): AppDetailActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ApiModule::class, MainActivityModule::class, ActivityNavigationModule::class])
    abstract fun provideWorkspaceAddActivity(): WorkspaceAddActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ApiModule::class, MainActivityModule::class, ActivityNavigationModule::class])
    abstract fun provideWorkspaceListActivity(): WorkspaceListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [ApiModule::class, MainActivityModule::class, ActivityNavigationModule::class])
    abstract fun provideSettingsActivity(): SettingsActivity
}