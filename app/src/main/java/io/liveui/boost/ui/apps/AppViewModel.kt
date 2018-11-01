package io.liveui.boost.ui.apps

import io.liveui.boost.api.model.App
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import javax.inject.Inject

class AppViewModel @Inject constructor(downloadManager: BoostDownloadManager, glideProvider: GlideProvider, ioUtil: IOUtil, contextProvider: ContextProvider) : BaseAppViewModel<App>(downloadManager, glideProvider, ioUtil, contextProvider) {

    fun openAppDetail() {
        AppDetailActivity.startActivity(contextProvider.app, nonNullApp.getAppId())
    }
}