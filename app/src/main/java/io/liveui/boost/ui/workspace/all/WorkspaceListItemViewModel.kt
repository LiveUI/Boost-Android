package io.liveui.boost.ui.workspace.all

import android.content.Intent
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.Icon
import io.liveui.boost.api.model.Message
import io.liveui.boost.api.model.ServerInfo
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.splash.SplashActivity
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.DensityIconMapper
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.ResourcesProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkspaceListItemViewModel @Inject constructor(val boostCheckUseCase: BoostCheckUseCase,
                                                     val densityIconMapper: DensityIconMapper,
                                                     val contextProvider: ContextProvider,
                                                     val workspaceDao: WorkspaceDao) : LifecycleViewModel() {

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

    //TODO fix
    fun changeWorkspace(workspace: Workspace) {
        GlobalScope.launch {
            workspaceDao.setActive(workspace)

            val context = contextProvider.app
            context.startActivity(Intent(context, SplashActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }

    fun onItemClick(onServerVerified: () -> Unit) {
        val status = workspace.value?.status
        val inactive = workspace.value?.active == 0
        val context = contextProvider.app
        when (status) {
            Workspace.Status.NEW -> {
                WorkspaceAddActivity.startActivity(context)
            }
            Workspace.Status.SERVER_VERIFIED -> {
                onServerVerified()
            }
            Workspace.Status.ACTIVATED -> {
                if (inactive) {
                    changeWorkspace(workspace.value!!)
                }
            }
        }
    }

}