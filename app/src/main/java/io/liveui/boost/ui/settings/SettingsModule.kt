package io.liveui.boost.ui.settings

import dagger.Module
import dagger.Provides

@Module
class SettingsModule {

    @Provides
    fun provideSettingsAdapter(): SettingsAdapter {
        return SettingsAdapter()
    }
}