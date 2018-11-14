package io.liveui.boost.ui.workspace.add

import android.util.Patterns
import android.webkit.URLUtil
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
import kotlinx.android.synthetic.main.fragment_intro.*
import timber.log.Timber
import java.net.URL
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


class WorkspaceAddViewModel @Inject constructor(private val checkUseCase: BoostCheckUseCase,
                                                private val workspaceDao: WorkspaceDao,
                                                private val urlProvider: UrlProvider,
                                                private val userSession: UserSession) : LifecycleViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    val suggestedUrl: MutableLiveData<Array<String>> = MutableLiveData()

    val serverTarget: MutableLiveData<ServerTarget> = MutableLiveData()

    val customServerUrl: MutableLiveData<String?> = MutableLiveData()

    val isUrlValid: LiveData<Boolean> = Transformations.map(customServerUrl) {
        it?.let { Pattern.matches(Patterns.WEB_URL.pattern(), it) } ?: false
    }

    val customUrlTextFieldVisibility: LiveData<Boolean> = Transformations.map(serverTarget) {
        it == ServerTarget.CUSTOM_SERVER
    }

    init {
        suggestedUrl.postValue(BuildConfig.URL)
        serverTarget.postValue(ServerTarget.CLIENT_SERVER)
    }

    fun checkServer(url: String?, onServerVerified: (workspace: Workspace) -> Unit = {}, onServerVerifyError: () -> Unit) {
        url?.let {
            if (!Pattern.matches(Patterns.WEB_URL.pattern(), it)) return

            addDisposable(checkUseCase.getInfo(it)
                    .doOnSubscribe {
                        loadingStatus.postValue(true)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({ serverInfo ->
                        Timber.i("Workspace created")
                        loadingStatus.postValue(false)
                        onServerVerified(createTempWorkspace(it, serverInfo))
                    }, { e ->
                        onServerVerifyError()
                        Timber.i(e, "Workspace create failed") //TODO resolve exception
                        loadingStatus.postValue(false)
                        exception.postValue(e)
                    })
            )
        } ?: onServerVerifyError()
    }

    fun createTempWorkspace(url: String, serverInfo: ServerInfo): Workspace {
        return Workspace(url = url).apply {
            name = serverInfo.name
            status = Workspace.Status.SERVER_VERIFIED
        }
    }

    fun onClientServerSelected() {
        serverTarget.postValue(ServerTarget.CLIENT_SERVER)
    }

    fun onCustomServerSelected() {
        serverTarget.postValue(ServerTarget.CUSTOM_SERVER)
    }

    fun verifyServer(onServerVerified: (workspace: Workspace) -> Unit, onServerVerifyError: () -> Unit) {
        serverTarget.value?.let {
            when (it) {
                ServerTarget.CLIENT_SERVER -> {
                    checkServer(urlProvider.getDefaultUrl(), onServerVerified, onServerVerifyError)
                }
                ServerTarget.CUSTOM_SERVER -> {
                    checkServer(customServerUrl.value, onServerVerified, onServerVerifyError)
                }
            }
        } ?: onServerVerifyError()
    }
}
