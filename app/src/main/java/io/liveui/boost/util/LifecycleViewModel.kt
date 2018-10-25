package io.liveui.boost.util

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class LifecycleViewModel : ViewModel(), LifecycleObserver {

    private val disposables: CompositeDisposable = CompositeDisposable()

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
        clearDisposables()
    }
}