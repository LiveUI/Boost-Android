package io.liveui.boost.util

import io.reactivex.disposables.Disposable

fun dispose(disposable: Disposable) {
    if (!disposable.isDisposed) {
        disposable.dispose()
    }
}
