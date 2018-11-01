package io.liveui.boost.ui

import androidx.lifecycle.MutableLiveData
import io.liveui.boost.util.ContextProvider
import io.liveui.boost.util.LifecycleViewModel
import timber.log.Timber
import javax.inject.Inject

class ToolbarViewModel @Inject constructor(contextProvider: ContextProvider) : LifecycleViewModel() {

    val title = MutableLiveData<String?>()
    val subtitle = MutableLiveData<String?>()

}