package io.liveui.boost.di

import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.api.usecase.BoostApiUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class ApiModule {

    @Provides
    fun providesApiViewModelFactory(apiUseCase: BoostApiUseCase):
            ApiViewModeFactory {
        return ApiViewModeFactory(apiUseCase)
    }
}