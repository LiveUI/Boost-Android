package io.liveui.boost.util

import android.app.Application
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextProvider @Inject constructor(val app: Application)