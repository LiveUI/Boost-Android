package io.liveui.boost.ui.overview.list

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.IApp
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IntentUtil
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.glide.GlideProvider

abstract class BaseAppViewModel<T : IApp>(val glideProvider: GlideProvider,
                                          val contextProvider: ContextProvider)
    : LifecycleViewModel() {

    var _app = MutableLiveData<T>()
    var app: T? = null
        set(value) {
            field = value
            value?.let {
                _app.postValue(it)
            }
        }

    val appName = Transformations.map(_app) {
        it.getAppName()
    }

    val appVersion = Transformations.map(_app) {
        it.getAppVersion()
    }

    val appIdentifier = Transformations.map(_app) {
        it.getAppIdentifier()
    }

    val id = Transformations.map(_app) {
        it.getAppId()
    }

    fun loadAppIcon(imageView: ImageView, appId: String) {
        if(app?.hasIcon() == true ) {
            glideProvider.loadAppIcon(imageView, appId)
        }
    }

    fun openAppList() {
        app?.let {
            AppsActivity.startActivity(contextProvider.app, it.getAppIdentifier())
        }
    }

    fun openSettings() {
        app?.let {
            IntentUtil.openSettings(contextProvider.app, it.getAppIdentifier())
        }
    }

    fun openAppDetail() {
        app?.let {
            AppDetailActivity.startActivity(contextProvider.app, it.getAppId())
        }
    }
}