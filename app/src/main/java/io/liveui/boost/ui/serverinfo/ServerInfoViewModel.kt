package io.liveui.boost.ui.serverinfo

import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.common.UserSession
import io.liveui.boost.util.DensityIconMapper
import javax.inject.Inject

class ServerInfoViewModel @Inject constructor(boostCheckUseCase: BoostCheckUseCase,
                                              densityIconMapper: DensityIconMapper,
                                              val userSession: UserSession) : BaseServerInfoViewModel(boostCheckUseCase, densityIconMapper) {
    init {
        userSession.activeWorkspace.observeForever { activeWorkspace ->
            activeWorkspace?.let {
                workspace.value = it
            }
        }
    }

}