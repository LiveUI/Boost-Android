package io.liveui.boost.util.ext

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.defaultValue(initialValue: T) = apply { postValue(initialValue) }
