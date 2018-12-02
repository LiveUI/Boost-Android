package io.liveui.boost.ui.serverinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.Icon
import io.liveui.boost.api.model.Message
import io.liveui.boost.api.model.ServerInfo
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.db.Workspace
import io.liveui.boost.util.DensityIconMapper
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.BackpressureStrategy

abstract class BaseServerInfoViewModel(val boostCheckUseCase: BoostCheckUseCase,
                                       val densityIconMapper: DensityIconMapper) : LifecycleViewModel() {

    val workspace: MutableLiveData<Workspace> = MutableLiveData()

    val serverInfo: LiveData<ServerInfo?> = Transformations.switchMap(workspace) {
        LiveDataReactiveStreams.fromPublisher(boostCheckUseCase.getInfo(it.url).toFlowable(BackpressureStrategy.BUFFER))
    }

    val serverName = Transformations.map(workspace) {
        it.name
    }

    val serverUrl = Transformations.map(workspace) {
        it.url
    }

    val allServerIcons: LiveData<List<Icon>?> = Transformations.map(serverInfo) {
        it?.icons
    }

    val iconUrl = Transformations.map(serverInfo) { info ->
        info?.icons?.firstOrNull {
            densityIconMapper.getValueForDensity() == it.size
        }?.url
    }

    val ping: LiveData<Message?> = Transformations.switchMap(workspace) {
        LiveDataReactiveStreams.fromPublisher(boostCheckUseCase.ping(it.url).toFlowable(BackpressureStrategy.BUFFER))
    }

    val isServerActive: LiveData<Boolean> = Transformations.map(ping) {
        it != null
    }

    val serverStatusIcon: LiveData<Int> = Transformations.map(isServerActive) {
        if (it == true) {
            io.liveui.boost.R.drawable.ic_server_active
        } else {
            io.liveui.boost.R.drawable.ic_server_inactive
        }
    }

}