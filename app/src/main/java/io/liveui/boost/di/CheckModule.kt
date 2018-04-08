package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.api.CheckViewModelFactory
import io.liveui.boost.api.usecase.BoostCheckUseCase

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class CheckModule {

    @Provides
    fun providesCheckViewModelFactory(checkUseCase: BoostCheckUseCase):
            CheckViewModelFactory {
        return CheckViewModelFactory(checkUseCase)
    }
}