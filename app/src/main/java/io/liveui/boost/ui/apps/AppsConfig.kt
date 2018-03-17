package io.liveui.boost.ui.apps

import android.arch.lifecycle.MutableLiveData
import io.liveui.boost.BuildConfig
import javax.inject.Inject

class AppsConfig @Inject constructor() {

    var platform: MutableLiveData<Platform> = MutableLiveData()
    var identifier: MutableLiveData<String> = MutableLiveData()

    enum class Platform {
        ANDROID, IOS
    }

    init {
        platform.value = Platform.ANDROID
        identifier.value = BuildConfig.APPLICATION_ID
    }
}