package io.liveui.boost.ui.login

import androidx.lifecycle.MutableLiveData
import io.liveui.boost.api.model.AuthRequest
import io.liveui.boost.api.model.AuthResponse
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val authUseCase: BoostAuthUseCase,
                                         private val workspaceDao: WorkspaceDao,
                                         private val userSession: UserSession) : LifecycleViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val auth: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()
    var workspace: Workspace? = null

    fun auth(username: String, password: String) {
        workspace?.url?.let {
            addDisposable(authUseCase.auth(it, AuthRequest(username, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        loadingStatus.postValue(true)
                    }
                    .subscribe({ result ->
                        loadingStatus.postValue(false)
                        saveAuthData(result)
                    }, { e ->
                        loadingStatus.postValue(false)
                        exception.value = e
                    })
            )
        }
    }

    fun saveAuthData(response: AuthResponse) {
        GlobalScope.launch {
            workspace?.let {
                it.permToken = response.token
                it.status = Workspace.Status.ACTIVATED
                it.user = response.user

                userSession.workspace = it
                workspaceDao.setActive(it)
            }

            auth.postValue(true)
        }
    }

}