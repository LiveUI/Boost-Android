package io.liveui.boost.ui.apps

import io.liveui.boost.api.model.App
import io.liveui.boost.download.BoostDownloadManager
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.IOUtil
import io.liveui.boost.util.glide.GlideProvider
import javax.inject.Inject

class AppsItemViewModel @Inject constructor(downloadManager: BoostDownloadManager,
                                            glideProvider: GlideProvider,
                                            ioUtil: IOUtil,
                                            contextProvider: ContextProvider)
    : DownloadAppViewModel<App>(downloadManager, ioUtil, glideProvider, contextProvider)