package io.liveui.boost.di

import io.liveui.boost.api.AuthViewModelFactory
import io.liveui.boost.api.usecase.BoostAuthUseCase
import dagger.Module
import dagger.Provides
import io.liveui.boost.db.WorkspaceDao

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class AuthModule {

    @Provides
    fun providesAuthViewModelFactory(authUseCase: BoostAuthUseCase, workspaceDao: WorkspaceDao):
            AuthViewModelFactory {
        return AuthViewModelFactory(authUseCase, workspaceDao)
    }

}