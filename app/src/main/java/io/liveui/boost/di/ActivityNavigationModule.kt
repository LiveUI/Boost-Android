package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import io.liveui.boost.util.navigation.SECONDARY_NAVIGATOR
import javax.inject.Named

@Module
class ActivityNavigationModule {

    @Provides
    @Named(MAIN_NAVIGATOR)
    fun provideNavigator(): MainNavigator = MainNavigator()

    @Provides
    @Named(SECONDARY_NAVIGATOR)
    fun provideSideNavigator(): MainNavigator = MainNavigator()
}