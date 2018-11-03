package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.vmfactory.AuthViewModelFactory
import io.liveui.boost.ui.login.LoginViewModel
import javax.inject.Provider

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class AuthModule {

    @Provides
    fun providesAuthViewModelFactory(liginViewModelProvider: Provider<LoginViewModel>):
            AuthViewModelFactory {
        return AuthViewModelFactory(liginViewModelProvider)
    }

}