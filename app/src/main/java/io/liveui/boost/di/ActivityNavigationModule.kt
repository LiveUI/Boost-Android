package io.liveui.boost.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.util.navigation.MainNavigator

@Module
class ActivityNavigationModule {

    @Provides
    @ActivityScope
    fun provideNavigator(): MainNavigator {
        return MainNavigator()
    }
}