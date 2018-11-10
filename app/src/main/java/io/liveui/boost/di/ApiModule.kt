package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.appdetail.AppDetailViewModel
import io.liveui.boost.ui.apps.AppsViewModel
import io.liveui.boost.ui.overview.OverviewViewModel
import io.liveui.boost.ui.register.RegisterViewModel
import io.liveui.boost.ui.teams.TeamsViewModel
import javax.inject.Provider

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class ApiModule {

    @Provides
    fun providesApiViewModelFactory(appViewModelProvider: Provider<AppsViewModel>,
                                    teamsViewModel: Provider<TeamsViewModel>,
                                    appDetailViewModel: Provider<AppDetailViewModel>,
                                    overviewViewModel: Provider<OverviewViewModel>,
                                    registerViewModel: Provider<RegisterViewModel>,
                                    toolbarViewModel: Provider<ToolbarViewModel>):
            ApiViewModeFactory {
        return ApiViewModeFactory(appViewModelProvider, teamsViewModel, appDetailViewModel, overviewViewModel, registerViewModel, toolbarViewModel)
    }
}