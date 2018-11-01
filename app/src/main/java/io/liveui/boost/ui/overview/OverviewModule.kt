package io.liveui.boost.ui.overview

import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class OverviewModule {

    @Provides
    fun provideOvewievAdapter(baseAppViewModelProvider: Provider<OverviewAppItemViewModel>): OverviewGridAdapter {
        return OverviewGridAdapter(baseAppViewModelProvider)
    }

//    @Provides
//    fun provideOvewievAdapter(baseAppViewModelProvider: Provider<OverviewAppItemViewModel>,): OverviewGridAdapter {
//        return OverviewGridAdapter(baseAppViewModelProvider)
//    }

}