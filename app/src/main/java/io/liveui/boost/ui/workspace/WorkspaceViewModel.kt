package io.liveui.boost.ui.workspace

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class WorkspaceViewModel constructor(private val checkUseCase: BoostCheckUseCase, private val workspaceDao: WorkspaceDao) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val serverExists: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun checkServer(workspace: Workspace) {
        disposables.add(checkUseCase.getInfo()
                .flatMap {
                    return@flatMap Observable.fromCallable {
                        workspaceDao.insertWorkspace(workspace.apply {
                            name = it.name
                            status = Workspace.Status.SERVER_VERIFIED
                        })
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.postValue(true)
                })
                .subscribe({
                    Timber.i("Workspace created")
                    loadingStatus.postValue(false)
                    serverExists.postValue(true)
                }, { e ->
                    Timber.i("Workspace create failed")
                    serverExists.postValue(false)
                    loadingStatus.postValue(false)
                    exception.postValue(e)
                })
        )
    }

}