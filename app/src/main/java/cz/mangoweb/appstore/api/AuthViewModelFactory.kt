package cz.mangoweb.appstore.api

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import cz.mangoweb.appstore.api.usecase.BoostAuthUseCase
import cz.mangoweb.appstore.ui.login.LoginViewModel


class AuthViewModelFactory constructor(private val authUseCase: BoostAuthUseCase, private val sharedPreferences: SharedPreferences): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authUseCase, sharedPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}