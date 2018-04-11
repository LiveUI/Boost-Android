package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.SplashViewModelFactory
import io.liveui.boost.db.WorkspaceDao

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class SplashModule {

    @Provides
    fun providesSplashViewModelFactory(userSession: UserSession, workspaceDao: WorkspaceDao):
            SplashViewModelFactory {
        return SplashViewModelFactory(userSession, workspaceDao)
    }
}