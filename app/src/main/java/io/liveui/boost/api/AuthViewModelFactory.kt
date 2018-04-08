package io.liveui.boost.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.common.model.Workspace
import io.liveui.boost.ui.login.LoginViewModel


class AuthViewModelFactory constructor(private val authUseCase: BoostAuthUseCase, private val workspace: Workspace): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authUseCase, workspace) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}