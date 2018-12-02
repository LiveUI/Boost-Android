package io.liveui.boost.ui.workspace.add

import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import io.liveui.boost.BuildConfig
import io.liveui.boost.EXTRA_WORKSPACE
import io.liveui.boost.api.model.ServerInfo
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.UiState
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.SingleLiveEvent
import io.liveui.boost.util.UrlProvider
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject


class WorkspaceAddViewModel @Inject constructor(private val checkUseCase: BoostCheckUseCase,
                                                private val workspaceDao: WorkspaceDao,
                                                private val urlProvider: UrlProvider,
                                                private val userSession: UserSession) : LifecycleViewModel() {

    val uiState = SingleLiveEvent<UiState>()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val suggestedUrl: MutableLiveData<Array<String>> = MutableLiveData()

    val workspace = Workspace(status = Workspace.Status.NEW)

    val serverTarget: MutableLiveData<ServerTarget> = MutableLiveData()

    val serverName: MutableLiveData<String?> = MutableLiveData()

    val serverUrl: MutableLiveData<String?> = MutableLiveData()

    val customServerUrl: MutableLiveData<String?> = MutableLiveData()

    val customServerName: MutableLiveData<String?> = MutableLiveData()

    val isUrlValid: LiveData<Boolean> = Transformations.map(serverUrl) {
        it?.let { Pattern.matches(Patterns.WEB_URL.pattern(), it) } ?: false
    }

    val customUrlTextFieldVisibility: LiveData<Boolean> = Transformations.map(serverTarget) {
        it == ServerTarget.CUSTOM_SERVER
    }

    init {
        val customUrlObserver = Observer<String?> {
            serverUrl.postValue(it)
        }

        val customNameObserver = Observer<String?> {
            serverName.value = it
        }

        serverTarget.observeForever {
            if (it == ServerTarget.CLIENT_SERVER) {
                serverUrl.postValue(urlProvider.getDefaultUrl())
                serverName.postValue(null)
                customServerUrl.removeObserver(customUrlObserver)
                customServerName.removeObserver(customUrlObserver)
            } else {
                customServerName.observeForever(customNameObserver)
                customServerUrl.observeForever(customUrlObserver)
            }
        }

        serverUrl.observeForever {
            workspace.url = it ?: ""
        }

        serverName.observeForever {
            workspace.name = it
        }

        suggestedUrl.postValue(BuildConfig.URL)
        serverTarget.postValue(ServerTarget.CLIENT_SERVER)
        customServerUrl.postValue(null)
    }

    fun onClientServerSelected() = serverTarget.postValue(ServerTarget.CLIENT_SERVER)

    fun onCustomServerSelected() = serverTarget.postValue(ServerTarget.CUSTOM_SERVER)

    fun onAddServerClick() = saveWorkspace(workspace) {
        checkServerIfExist {
            updateWorkspace(workspace) {
                uiState.postValue(UiState.Success(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true, args = Bundle().apply {
                    putParcelable(EXTRA_WORKSPACE, it)
                })))
            }
        }
    }

    fun onLoginClick() = checkServerIfExist {
        uiState.postValue(UiState.Success(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true, args = Bundle().apply {
            putParcelable(EXTRA_WORKSPACE, it)
        })))
    }

    fun onRegisterClick() = checkServerIfExist {
        uiState.postValue(UiState.Success(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true, args = Bundle().apply {
            putParcelable(EXTRA_WORKSPACE, it)
        })))
    }

    private fun saveWorkspace(workspace: Workspace, onSaved: () -> Unit) = GlobalScope.launch {
        workspaceDao.insertWorkspace(workspaces = workspace)
        onSaved()
    }

    private fun updateWorkspace(workspace: Workspace, onUpdate: () -> Unit) = GlobalScope.launch {
        workspaceDao.updateWorkspace(workspace = workspace)
        onUpdate()
    }

    private fun checkServerIfExist(onVerified: (workspace: Workspace) -> Unit) = addDisposable(checkUseCase.getInfo(workspace.url)
            .doOnSubscribe {
                loadingStatus.postValue(true)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ serverInfo ->
                Timber.i("Workspace created")
                updateWorkspaceInfo(workspace, serverInfo)
                loadingStatus.postValue(false)
                onVerified(workspace)
            }, { e ->
                Timber.e(e, "Workspace create failed")
                loadingStatus.postValue(false)
                uiState.postValue(UiState.Error(e))
            })
    )

    private fun updateWorkspaceInfo(workspace: Workspace, serverInfo: ServerInfo): Workspace = workspace.apply {
        name = serverInfo.name
        status = Workspace.Status.SERVER_VERIFIED
    }
}
