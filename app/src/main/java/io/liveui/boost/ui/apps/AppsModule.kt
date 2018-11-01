package io.liveui.boost.ui.apps

import dagger.Module
import dagger.Provides
import io.liveui.boost.api.model.App
import io.liveui.boost.ui.ToolbarViewModel
import javax.inject.Provider

@Module
class AppsModule {

    @Provides
    fun provideAppsAdapter(baseAppViewModelProvider: Provider<AppViewModel>): AppsAdapter {
        return AppsAdapter(baseAppViewModelProvider)
    }

}