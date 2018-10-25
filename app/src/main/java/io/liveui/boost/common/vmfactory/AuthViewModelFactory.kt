package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.api.usecase.BoostAuthUseCase
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.ui.login.LoginViewModel


class AuthViewModelFactory constructor(private val authUseCase: BoostAuthUseCase,
                                       private val workspaceDao: WorkspaceDao): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authUseCase, workspaceDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}