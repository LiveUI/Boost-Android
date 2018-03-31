package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.api.DownloadManager
import io.liveui.boost.api.usecase.BoostDownloadUseCase

@Module
class DownloadModule {

    @Provides
    fun provideDownloadManager(downloadUseCase: BoostDownloadUseCase): DownloadManager {
        return DownloadManager(downloadUseCase)
    }
}