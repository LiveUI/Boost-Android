package io.liveui.boost.ui

import androidx.lifecycle.MutableLiveData
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.ext.defaultValue
import javax.inject.Inject

class UiActivityViewModel @Inject constructor() : LifecycleViewModel() {

    val background = MutableLiveData<Int>().defaultValue(android.R.color.transparent)

}