package io.liveui.boost.di

import android.content.SharedPreferences
import io.liveui.boost.api.AuthViewModelFactory
import io.liveui.boost.api.usecase.BoostAuthUseCase
import dagger.Module
import dagger.Provides
import io.liveui.boost.common.model.Workspace

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class AuthModule {

    @Provides
    fun providesAuthViewModelFactory(authUseCase: BoostAuthUseCase, workspace: Workspace):
            AuthViewModelFactory {
        return AuthViewModelFactory(authUseCase, workspace)
    }

}