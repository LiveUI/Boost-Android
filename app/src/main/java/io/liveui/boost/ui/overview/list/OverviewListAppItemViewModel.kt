package io.liveui.boost.ui.overview.list

import io.liveui.boost.api.model.App
import io.liveui.boost.api.model.IApp
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.ui.appdetail.AppDetailActivity
import io.liveui.boost.ui.apps.DownloadAppViewModel
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import javax.inject.Inject

class OverviewListAppItemViewModel @Inject constructor(downloadManager: BoostDownloadManager,
                                                       glideProvider: GlideProvider,
                                                       ioUtil: IOUtil,
                                                       contextProvider: ContextProvider)
    : DownloadAppViewModel<App>(downloadManager, ioUtil, glideProvider, contextProvider)