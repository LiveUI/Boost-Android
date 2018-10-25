package io.liveui.boost.ui.overview

import dagger.Module
import dagger.Provides
import io.liveui.boost.ui.apps.AppsItemViewModel
import javax.inject.Provider

@Module
class OverviewModule {

    @Provides
    fun provideOvewievAdapter(appsItemViewModelProvider: Provider<AppsItemViewModel>): OverviewAdapter {
        return OverviewAdapter(appsItemViewModelProvider)
    }

}