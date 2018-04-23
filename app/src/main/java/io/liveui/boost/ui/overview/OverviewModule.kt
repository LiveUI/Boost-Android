package io.liveui.boost.ui.overview

import dagger.Module
import dagger.Provides

@Module
class OverviewModule {

    @Provides
    fun provideOvewievAdapter(): OverviewAdapter {
        return OverviewAdapter()
    }
}