package io.liveui.boost.di

import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.api.usecase.BoostApiUseCase
import dagger.Module
import dagger.Provides
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class ApiModule {

    @Provides
    fun providesApiViewModelFactory(apiUseCase: BoostApiUseCase,
                                    downloadManager: BoostDownloadManager,
                                    glideProvider: GlideProvider,
                                    ioUtil: IOUtil,
                                    contextProvider: ContextProvider):
            ApiViewModeFactory {
        return ApiViewModeFactory(apiUseCase, downloadManager, glideProvider, ioUtil, contextProvider)
    }
}