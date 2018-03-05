package cz.mangoweb.appstore.di

import cz.mangoweb.appstore.api.ApiViewModeFactory
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
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