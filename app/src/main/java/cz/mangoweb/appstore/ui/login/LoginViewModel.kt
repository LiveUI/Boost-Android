package cz.mangoweb.appstore.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import cz.mangoweb.appstore.api.model.App
import cz.mangoweb.appstore.api.model.AuthRequest
import cz.mangoweb.appstore.api.model.AuthResponse
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
import cz.mangoweb.appstore.api.usecase.BoostAuthUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoginViewModel constructor(private val authUseCase: BoostAuthUseCase, private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val auth: MutableLiveData<AuthResponse> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun loadApps(username: String, password: String) {
        disposables.add(authUseCase.auth(AuthRequest(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result ->
                    auth.value = result
                    auth.let {
                        sharedPreferences.edit().putString("token", it.value?.token).apply()
                    }
                }, { e -> exception.value = e })
        )
    }
}