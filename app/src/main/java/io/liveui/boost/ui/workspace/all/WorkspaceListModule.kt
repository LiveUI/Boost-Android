package io.liveui.boost.ui.workspace.all

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.vmfactory.WorkspaceModelFactory
import io.liveui.boost.db.WorkspaceDao

@Module
class WorkspaceListModule {

    @Provides
    fun providesWorkspaceListModelFactory(workspaceDao: WorkspaceDao):
            WorkspaceModelFactory {
        return WorkspaceModelFactory(workspaceDao)
    }

    @Provides
    fun provideAppsAdapter(): WorkspaceListAdapter {
        return WorkspaceListAdapter()
    }
}