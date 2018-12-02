package io.liveui.boost.ui.view.adapter

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BoostRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    //TODO move everything to separate class and use attach and detach listeners + add extension
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val subjects = HashSet<Disposable>()

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
        subjects.add(disposable)
    }

    fun deleteDisposable(disposable: Disposable) {
        disposables.delete(disposable)
        subjects.remove(disposable)
    }

    fun clearDisposables() {
        disposables.clear()
        subjects.clear()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //re-register disposables
        subjects.filter { !it.isDisposed }.forEach {
            disposables.add(it)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clearDisposables()
    }

}