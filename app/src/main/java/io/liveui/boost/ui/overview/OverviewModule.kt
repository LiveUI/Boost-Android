package io.liveui.boost.ui.overview

import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class OverviewModule {

    @Provides
    fun provideOvewievAdapter(baseGridViewModelProvider: Provider<OverviewGridItemViewModel>): OverviewGridAdapter {
        return OverviewGridAdapter(baseGridViewModelProvider)
    }

    @Provides
    fun provideOvewievListAdapter(baseGridViewModelProvider: Provider<OverviewListItemViewModel>): OverviewListAdapter {
        return OverviewListAdapter(baseGridViewModelProvider)
    }

}