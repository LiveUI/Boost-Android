package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.ui.splash.SplashViewModel
import javax.inject.Inject
import javax.inject.Provider


class SplashViewModelFactory @Inject constructor(val splashViewModelProvider: Provider<SplashViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return splashViewModelProvider.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}