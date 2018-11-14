package io.liveui.boost.util

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class LifecycleViewModel : ViewModel(), LifecycleObserver {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun deleteDisposable(disposable: Disposable) {
        disposables.delete(disposable)
    }

    fun clearDisposables() {
        disposables.clear()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        clearDisposables()
    }
}