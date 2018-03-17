package io.liveui.boost.ui.apps

import dagger.Module
import dagger.Provides

@Module
class AppsModule {

    @Provides
    fun provideAppsAdapter(): AppsAdapter {
        return AppsAdapter()
    }
}