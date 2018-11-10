package io.liveui.boost.ui.workspace.add

import android.util.Patterns
import androidx.lifecycle.*
import io.liveui.boost.BuildConfig
import io.liveui.boost.api.model.ServerInfo
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.UrlProvider
import io.liveui.boost.util.ext.defaultValue
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.URL
import java.util.regex.Pattern
import javax.inject.Inject


class WorkspaceAddViewModel @Inject constructor(private val checkUseCase: BoostCheckUseCase,
                                                private val workspaceDao: WorkspaceDao,
                                                private val urlProvider: UrlProvider,
                                                private val userSession: UserSession) : LifecycleViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val serverExists: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    val suggestedUrl: MutableLiveData<Array<String>> = MutableLiveData()

    val serverTarget: MutableLiveData<ServerTarget> = MutableLiveData()

    val customServerUrl: MutableLiveData<String?> = MutableLiveData()

    val isCustomUrlValid: LiveData<Boolean> = Transformations.map(customServerUrl) {
        it?.let { Patterns.WEB_URL.matcher(it).matches() } ?: false
    }

    val customUrlTextFieldVisibility: LiveData<Boolean> = Transformations.map(serverTarget) {
        it == ServerTarget.CUSTOM_SERVER
    }

    init {
        suggestedUrl.postValue(BuildConfig.URL)
        serverTarget.postValue(ServerTarget.CLIENT_SERVER)
    }

    fun checkServer(url: String) {
        val workspace = Workspace(url = url)
        addDisposable(checkUseCase.getInfo(url)
                .map {
                    workspace.apply {
                        name = it.name
                        status = Workspace.Status.SERVER_VERIFIED
                    }
                }.flatMapCompletable {
                    userSession.workspace = it
                    workspaceDao.setActive(it)
                }.doOnSubscribe {
                    loadingStatus.postValue(true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    Timber.i("Workspace created")
                    loadingStatus.postValue(false)
                    serverExists.postValue(true)
                }, { e ->
                    Timber.i(e, "Workspace create failed") //TODO resolve exception
                    serverExists.postValue(false)
                    loadingStatus.postValue(false)
                    exception.postValue(e)
                })
        )
    }

    fun onClientServerSelected() {
        serverTarget.postValue(ServerTarget.CLIENT_SERVER)
    }

    fun onCustomServerSelected() {
        serverTarget.postValue(ServerTarget.CUSTOM_SERVER)

    }

    fun onLoginClicked() {
        serverTarget.value?.let {
            when (it) {
                ServerTarget.CLIENT_SERVER -> {
                    checkServer(urlProvider.getDefaultUrl())
                }
                ServerTarget.CUSTOM_SERVER -> {
                    isCustomUrlValid.value?.let {
                        checkServer(customServerUrl.value!!)
                    }
                }
            }
        } ?: onServerUrlError()
    }

    fun onServerUrlError() {

    }

}