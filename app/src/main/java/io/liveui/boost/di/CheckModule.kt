package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.common.vmfactory.CheckViewModelFactory
import io.liveui.boost.db.WorkspaceDao

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class CheckModule {

    @Provides
    fun providesCheckViewModelFactory(checkUseCase: BoostCheckUseCase, workspaceDao: WorkspaceDao):
            CheckViewModelFactory {
        return CheckViewModelFactory(checkUseCase, workspaceDao)
    }
}