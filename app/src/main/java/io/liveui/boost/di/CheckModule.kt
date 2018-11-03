package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.vmfactory.CheckViewModelFactory
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import javax.inject.Provider

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class CheckModule {

    @Provides
    fun providesCheckViewModelFactory(workspaceAddViewModelProvider: Provider<WorkspaceAddViewModel>):
            CheckViewModelFactory {
        return CheckViewModelFactory(workspaceAddViewModelProvider)
    }
}