package io.liveui.boost.ui.apps

import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class AppsModule {

    @Provides
    fun provideAppsAdapter(appsItemViewModelProvider: Provider<AppsItemViewModel>): AppsAdapter {
        return AppsAdapter(appsItemViewModelProvider)
    }

}