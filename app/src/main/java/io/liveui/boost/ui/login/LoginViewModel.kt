package io.liveui.boost.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.liveui.boost.api.model.AuthRequest
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginViewModel @Inject constructor(private val authUseCase: BoostAuthUseCase,
                                         private val workspaceDao: WorkspaceDao,
                                         private val userSession: UserSession) : LifecycleViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val auth: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()


    fun auth(username: String, password: String) {
        addDisposable(authUseCase.auth(AuthRequest(username, password))
                .flatMap {
                    return@flatMap Observable.fromCallable {
                        workspaceDao.updateWorkspace(userSession.workspace!!.apply {
                            permToken = it.token
                            status = Workspace.Status.ACTIVATED
                            user = it.user
                        })
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.postValue(true)
                }
                .subscribe({ result ->
                    loadingStatus.postValue(false)
                    auth.postValue(true)
                }, { e ->
                    loadingStatus.postValue(false)
                    exception.value = e
                })
        )
    }

}