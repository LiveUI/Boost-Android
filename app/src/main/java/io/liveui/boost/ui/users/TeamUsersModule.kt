package io.liveui.boost.ui.users

import dagger.Module
import dagger.Provides

@Module
class TeamUsersModule {

    @Provides
    fun provideTeamUsersAdapter(): TeamUserAdapter {
        return TeamUserAdapter()
    }
}