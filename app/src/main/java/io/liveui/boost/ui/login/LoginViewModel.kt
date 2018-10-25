package io.liveui.boost.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.liveui.boost.api.model.AuthRequest
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel constructor(private val authUseCase: BoostAuthUseCase,
                                 private val workspaceDao: WorkspaceDao) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val auth: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun auth(username: String, password: String, workspace: Workspace) {
        disposables.add(authUseCase.auth(AuthRequest(username, password))
                .flatMap {
                    return@flatMap Observable.fromCallable {
                        workspaceDao.updateWorkspace(workspace.apply {
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