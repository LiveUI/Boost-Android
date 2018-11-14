package io.liveui.boost.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.liveui.boost.ui.account.MyAccountFragment
import io.liveui.boost.ui.appdetail.AppDetailFragment
import io.liveui.boost.ui.apps.AppsFragment
import io.liveui.boost.ui.keys.ApiKeysFragment
import io.liveui.boost.ui.overview.OverviewFragment
import io.liveui.boost.ui.settings.SettingsFragment
import io.liveui.boost.ui.teams.CreateTeamFragment
import io.liveui.boost.ui.teams.TeamsFragment
import io.liveui.boost.ui.users.TeamUsersFragment
import io.liveui.boost.ui.users.UsersFragment
import io.liveui.boost.ui.workspace.add.WorkspaceAddFragment
import io.liveui.boost.ui.workspace.all.WorkspaceListFragment

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun proviedeAppsFragment(): AppsFragment

    @ContributesAndroidInjector
    abstract fun provideAppDetailFragment(): AppDetailFragment

    @ContributesAndroidInjector
    abstract fun provideTeamsFragment(): TeamsFragment

    @ContributesAndroidInjector
    abstract fun provideMyAccountFragment(): MyAccountFragment

    @ContributesAndroidInjector
    abstract fun provideApiKeysFragment(): ApiKeysFragment

    @ContributesAndroidInjector
    abstract fun provideSettingsFragment(): SettingsFragment

    @ContributesAndroidInjector
    abstract fun provideCreateTeamFragment(): CreateTeamFragment

    @ContributesAndroidInjector
    abstract fun provideTeamUsersFragment(): TeamUsersFragment

    @ContributesAndroidInjector
    abstract fun provideUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun provideWorkspaceAddFragment(): WorkspaceAddFragment

    @ContributesAndroidInjector
    abstract fun provideWorkspaceListFragment(): WorkspaceListFragment

    @ContributesAndroidInjector
    abstract fun provideOverviewFragment(): OverviewFragment

}