package io.liveui.boost.ui.overview

import dagger.Module
import dagger.Provides
import io.liveui.boost.ui.overview.grid.OverviewGridAdapter
import io.liveui.boost.ui.overview.grid.OverviewGridItemViewModel
import io.liveui.boost.ui.overview.list.OverviewListAdapter
import io.liveui.boost.ui.overview.list.OverviewListAppItemViewModel
import io.liveui.boost.ui.overview.list.OverviewListItemViewModel
import javax.inject.Provider

@Module
class OverviewModule {

    @Provides
    fun provideOverviewAdapter(overviewGridItemViewModel: Provider<OverviewGridItemViewModel>): OverviewGridAdapter {
        return OverviewGridAdapter(overviewGridItemViewModel)
    }

    @Provides
    fun provideOverviewListAdapter(overviewListItemViewModelProvider: Provider<OverviewListItemViewModel>,
                                   overviewListAppItemViewModelProvider: Provider<OverviewListAppItemViewModel>): OverviewListAdapter {
        return OverviewListAdapter(overviewListItemViewModelProvider, overviewListAppItemViewModelProvider)
    }

}