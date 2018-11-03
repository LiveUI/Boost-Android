package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.vmfactory.SplashViewModelFactory
import io.liveui.boost.ui.splash.SplashViewModel
import javax.inject.Provider

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class SplashModule {

    @Provides
    fun providesSplashViewModelFactory(splashViewModelProvider: Provider<SplashViewModel>):
            SplashViewModelFactory {
        return SplashViewModelFactory(splashViewModelProvider)
    }
}