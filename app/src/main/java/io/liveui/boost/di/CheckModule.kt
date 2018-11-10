package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.ui.workspace.add.WorkspaceAddViewModel
import io.liveui.boost.ui.workspace.all.WorkspaceListViewModel
import javax.inject.Provider

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class CheckModule {

    @Provides
    fun providesCheckViewModelFactory(workspaceViewModelProvider: Provider<WorkspaceListViewModel>,
                                      workspaceAddViewModelProvider: Provider<WorkspaceAddViewModel>):
            WorkspaceModelFactory {
        return WorkspaceModelFactory(workspaceViewModelProvider, workspaceAddViewModelProvider)
    }
}