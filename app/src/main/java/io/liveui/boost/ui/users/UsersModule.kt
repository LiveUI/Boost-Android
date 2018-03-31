package io.liveui.boost.ui.users

import dagger.Module
import dagger.Provides

@Module
class UsersModule {

    @Provides
    fun provideUsersAdapter(): UsersAdapter {
        return UsersAdapter()
    }
}