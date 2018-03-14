package cz.mangoweb.appstore.ui.register

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import cz.mangoweb.appstore.api.model.*
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
import cz.mangoweb.appstore.api.usecase.BoostAuthUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class RegisterViewModel constructor(private val apiUseCase: BoostApiUseCase) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val register: MutableLiveData<RegisterUserResponse> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    //TODO validate input data
    fun register(firstName: String, lastName: String, email: String, password: String) {
        disposables.add(apiUseCase.registerUser(RegisterUser(firstName, lastName, email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .subscribe({ result ->
                    loadingStatus.value = false
                    register.value = result
                }, { e ->
                    loadingStatus.value = false
                    exception.value = e
                })
        )
    }
}