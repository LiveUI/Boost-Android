package io.liveui.boost.ui.register

import androidx.lifecycle.MutableLiveData
import io.liveui.boost.api.model.RegisterUser
import io.liveui.boost.api.model.User
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.db.Workspace
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RegisterViewModel @Inject constructor(private val authUseCase: BoostAuthUseCase) : LifecycleViewModel() {

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val register: MutableLiveData<User> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    var workspace: Workspace? = null

    //TODO validate input data
    fun register(firstName: String, lastName: String, username: String, email: String, password: String) {
        workspace?.url?.let {
            addDisposable(authUseCase.registerUser(it, RegisterUser(firstName, lastName, username, email, password))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        loadingStatus.value = true
                    }
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
}