package cz.mangoweb.appstore.di

import android.content.SharedPreferences
import cz.mangoweb.appstore.api.AuthViewModelFactory
import cz.mangoweb.appstore.api.usecase.BoostAuthUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class AuthModule {

    @Provides
    fun providesAuthViewModelFactory(authUseCase: BoostAuthUseCase, sharedPreferences: SharedPreferences):
            AuthViewModelFactory {
        return AuthViewModelFactory(authUseCase, sharedPreferences)
    }

}