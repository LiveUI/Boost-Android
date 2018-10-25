package io.liveui.boost.ui.workspace.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WorkspaceListViewModel(val workspaceDao: WorkspaceDao) : ViewModel() {

    val disposables: CompositeDisposable = CompositeDisposable()

    fun loadWorkspace(): LiveData<MutableList<Workspace>> {
        return workspaceDao.getWorkspaces()
    }

    fun changeWorkspace(workspace: Workspace) {
        disposables.add(Completable.fromCallable {
            return@fromCallable workspaceDao.setActive(workspace)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe())

    }
}