package io.liveui.boost.ui.overview

import io.liveui.boost.api.model.IApp
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.ui.apps.BaseAppViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import javax.inject.Inject

class OverviewGridItemViewModel @Inject constructor(downloadManager: BoostDownloadManager,
                                                    glideProvider: GlideProvider,
                                                    ioUtil: IOUtil,
                                                    contextProvider: ContextProvider)
    : BaseAppViewModel<IApp>(downloadManager, glideProvider, ioUtil, contextProvider) {

    fun openAppList() {
        app?.let {
            AppsActivity.startActivity(contextProvider.app, it.getAppIdentifier())
        }
    }
}