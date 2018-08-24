package io.liveui.boost.di

import io.liveui.boost.ui.appdetail.AppDetailFragment
import io.liveui.boost.ui.MainActivity
import io.liveui.boost.ui.apps.AppsFragment
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.teams.TeamsActivity
import io.liveui.boost.ui.teams.TeamsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.liveui.boost.ui.account.MyAccountFragment
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.apps.AppsModule
import io.liveui.boost.ui.intro.ChooseServerFragment
import io.liveui.boost.ui.keys.ApiKeysFragment
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.ui.overview.OverviewFragment
import io.liveui.boost.ui.overview.OverviewModule
import io.liveui.boost.ui.register.RegisterFragment
import io.liveui.boost.ui.settings.SettingsFragment
import io.liveui.boost.ui.settings.SettingsModule
import io.liveui.boost.ui.teams.CreateTeamFragment
import io.liveui.boost.ui.teams.TeamModule
import io.liveui.boost.ui.users.TeamUsersFragment
import io.liveui.boost.ui.users.TeamUsersModule
import io.liveui.boost.ui.users.UsersFragment
import io.liveui.boost.ui.users.UsersModule
import io.liveui.boost.ui.workspace.add.WorkspaceAddFragment
import io.liveui.boost.ui.workspace.all.WorkspaceListFragment
import io.liveui.boost.ui.workspace.all.WorkspaceListModule

@Module(includes = [AndroidSupportInjectionModule::class])
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [AuthModule::class, BaseActivityModule::class])
    abstract fun provideLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [ApiModule::class, AppsModule::class, BaseActivityModule::class])
    abstract fun provideAppsActivity(): MainActivity

    @ContributesAndroidInjector(modules = [ApiModule::class, BaseActivityModule::class])
    abstract fun provideTeamsActivity(): TeamsActivity

    @ContributesAndroidInjector(modules = [ApiModule::class, BaseActivityModule::class])
    abstract fun provideAppDetailsActivity(): AppDetailActivity

    @ContributesAndroidInjector(modules = [ApiModule::class, AppsModule::class])
    abstract fun proviedeAppsFragment(): AppsFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideAppDetailFragment(): AppDetailFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, TeamModule::class])
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

    @ContributesAndroidInjector(modules = [CheckModule::class])
    abstract fun provideWorkspaceAddFragment(): WorkspaceAddFragment

    @ContributesAndroidInjector(modules = [WorkspaceListModule::class])
    abstract fun provideWorkspaceListFragment(): WorkspaceListFragment

    @ContributesAndroidInjector(modules = [MainActivityModule::class, ApiModule::class, OverviewModule::class])
    abstract fun provideOverviewFragment(): OverviewFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, CheckModule::class])
    abstract fun provideChooseServerFragment(): ChooseServerFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, AuthModule::class])
    abstract fun provideLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideRegisterFragment(): RegisterFragment

}