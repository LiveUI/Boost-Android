package io.liveui.boost.ui.workspace

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.liveui.boost.api.model.App
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.api.usecase.BoostCheckUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class WorkspaceViewModel constructor(private val checkUseCase: BoostCheckUseCase) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val serverExists: MutableLiveData<Boolean> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun checkServer() {
        disposables.add(checkUseCase.ping()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .subscribe({
                    loadingStatus.value = false
                    serverExists.value = true
                }, { e ->
                    serverExists.value = false
                    loadingStatus.value = false
                    exception.value = e
                })
        )
    }
}