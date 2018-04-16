package io.liveui.boost.ui.teams

import dagger.Module
import dagger.Provides

@Module
class TeamModule {

    @Provides
    fun provideTeamsAdapter(): TeamsAdapter {
        return TeamsAdapter()
    }
}