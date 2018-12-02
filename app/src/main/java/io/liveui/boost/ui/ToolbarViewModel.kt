package io.liveui.boost.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.ext.defaultValue
import javax.inject.Inject

class ToolbarViewModel @Inject constructor() : LifecycleViewModel() {

    val title = MutableLiveData<String?>()
    val subtitle = MutableLiveData<String?>()
    val show = MutableLiveData<Int>().defaultValue(View.VISIBLE)

}