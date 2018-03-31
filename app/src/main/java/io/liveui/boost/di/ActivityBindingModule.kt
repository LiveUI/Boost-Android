package io.liveui.boost.di

import io.liveui.boost.ui.appdetail.AppDetailFragment
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.ui.apps.AppsFragment
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.teams.TeamsActivity
import io.liveui.boost.ui.teams.TeamsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.liveui.boost.ui.account.MyAccountFragment
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.apps.AppsModule
import io.liveui.boost.ui.keys.ApiKeysFragment
import io.liveui.boost.ui.settings.SettingsFragment
import io.liveui.boost.ui.settings.SettingsModule
import io.liveui.boost.ui.teams.CreateTeamFragment
import io.liveui.boost.ui.users.TeamUsersFragment
import io.liveui.boost.ui.users.TeamUsersModule
import io.liveui.boost.ui.users.UsersFragment
import io.liveui.boost.ui.users.UsersModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun provideLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [ApiModule::class, AppsModule::class])
    abstract fun provideAppsActivity(): AppsActivity

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideTeamsActivity(): TeamsActivity

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideAppDetailsActivity(): AppDetailActivity

    @ContributesAndroidInjector
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [ApiModule::class, AppsModule::class])
    abstract fun proviedeAppsFragment(): AppsFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideAppDetailFragment(): AppDetailFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideTeamsFragment(): TeamsFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideMyAccountFragment(): MyAccountFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideApiKeysFragment(): ApiKeysFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, SettingsModule::class])
    abstract fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideCreateTeamFragment(): CreateTeamFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, TeamUsersModule::class])
    abstract fun provideTeamUsersFragment(): TeamUsersFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, UsersModule::class])
    abstract fun provideUsersFragment(): UsersFragment

}