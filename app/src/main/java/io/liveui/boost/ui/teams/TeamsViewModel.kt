package io.liveui.boost.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.liveui.boost.api.model.*
import io.liveui.boost.api.usecase.BoostApiUseCase
import io.liveui.boost.util.LifecycleViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TeamsViewModel @Inject constructor(private val apiUseCase: BoostApiUseCase) : LifecycleViewModel() {

    val activeTeam: MutableLiveData<Team?> = MutableLiveData()

    val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    val teams: MutableLiveData<MutableList<Team>> = MutableLiveData()

    val teamCreate: MutableLiveData<Team> = MutableLiveData()

    val teamCheck: MutableLiveData<TeamCheckResponse> = MutableLiveData()

    val team: MutableLiveData<Team> = MutableLiveData()

    val teamUpdate: MutableLiveData<Team> = MutableLiveData()

    val users: MutableLiveData<MutableList<User>> = MutableLiveData()

    val exception: MutableLiveData<Throwable> = MutableLiveData()

    val teamInfo: LiveData<TeamInfo> = Transformations.switchMap(activeTeam) { currentTeam ->
        currentTeam?.let {
            LiveDataReactiveStreams.fromPublisher(getTeamInfoObservable(currentTeam.id).toFlowable(BackpressureStrategy.LATEST))
        }
    }

    fun getTeamsObservable(): Observable<MutableList<Team>> {
        return apiUseCase.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    teams.value = it
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun loadTeams() {
        addDisposable(getTeamsObservable().doOnComplete {
            activeTeam.postValue(null)
        }.subscribe())
    }

    fun getCreateTeamObservable(team: CreateTeamRequest): Observable<Team> {
        return apiUseCase.createTeam(team)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    teamCreate.value = it
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun createTeam(team: CreateTeamRequest) {
        addDisposable(getCreateTeamObservable(team).subscribe())
    }

    fun getCheckTeamObservable(team: TeamCheckRequest): Observable<TeamCheckResponse> {
        return apiUseCase.checkTeam(team)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    teamCheck.value = it
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun checkTeam(team: TeamCheckRequest) {
        addDisposable(getCheckTeamObservable(team).subscribe())
    }

    fun getTeamObservable(id: String): Observable<Team> {
        return apiUseCase.getTeam(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    team.value = it
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun getTeam(id: String) {
        addDisposable(getTeamObservable(id).subscribe())
    }

    fun getUpdateTeamObservable(team: Team): Observable<Team> {
        return apiUseCase.updateTeam(team.id, team)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    teamUpdate.value = it
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun updateTeam(team: Team) {
        addDisposable(getUpdateTeamObservable(team).subscribe())
    }

    fun getTeamUsersObservable(id: String): Observable<MutableList<User>> {
        return apiUseCase.getTeamUsers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnNext {
                    loadingStatus.value = false
                    users.value = it
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun getTeamUsers(id: String) {
        addDisposable(getTeamUsersObservable(id).subscribe())
    }

    fun getAddUserToTeamCompletable(id: String, user: User): Completable {
        return apiUseCase.addUserToTeam(id, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnComplete {
                    loadingStatus.value = false
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun addUserToTeam(id: String, user: User) {
        addDisposable(getAddUserToTeamCompletable(id, user).subscribe())
    }

    fun getRemoveUserFromTeamCompletable(id: String, user: User): Completable {
        return apiUseCase.removeUserFromTeam(id, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.value = true
                }
                .doOnComplete {
                    loadingStatus.value = false
                }
                .doOnError {
                    loadingStatus.value = false
                    exception.value = it
                }
    }

    fun removeUserFromTeam(id: String, user: User) {
        addDisposable(getRemoveUserFromTeamCompletable(id, user).subscribe())
    }

    fun getTeamInfoObservable(id: String): Observable<TeamInfo> {
        return apiUseCase.getTeamInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingStatus.postValue(true)
                }
                .doOnNext {
                    loadingStatus.postValue(false)
                }
                .doOnError {
                    loadingStatus.postValue(false)
                    exception.postValue(it)
                }
    }
}