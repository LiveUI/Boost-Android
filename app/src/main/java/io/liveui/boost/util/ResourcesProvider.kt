package io.liveui.boost.util

import android.app.Application
import android.content.res.Resources
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesProvider @Inject constructor(val application: Application) {

    fun getResources(): Resources =  application.resources

    fun getDisplayDensity(): Float {
        return getResources().displayMetrics.density
    }
}