package cz.mangoweb.appstore.ui.teams

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import cz.mangoweb.appstore.api.model.*
import cz.mangoweb.appstore.api.usecase.BoostApiUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TeamsViewModel constructor(private val apiUseCase: BoostApiUseCase) : ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val loadingStatus: MutableLiveData<Boolean> = MutableLiveData()

    private val teams: MutableLiveData<List<Team>> = MutableLiveData()

    private val teamCreate: MutableLiveData<Team> = MutableLiveData()

    private val teamCheck: MutableLiveData<TeamCheckResponse> = MutableLiveData()

    private val team: MutableLiveData<Team> = MutableLiveData()

    private val teamUpdate: MutableLiveData<Team> = MutableLiveData()

    private val teamUsers: MutableLiveData<List<TeamUser>> = MutableLiveData()

    private val exception: MutableLiveData<Throwable> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun getLoadingStatus(): MutableLiveData<Boolean> {
        return loadingStatus
    }

    fun getException(): MutableLiveData<Throwable> {
        return exception
    }

    fun loadTeams() {
        disposables.add(apiUseCase.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> teams.value = result },
                        { e -> exception.value = e })
        )
    }

    fun createTeam(team: CreateTeamRequest) {
        disposables.add(apiUseCase.createTeam(team)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> teamCreate.value = result },
                        { e -> exception.value = e })
        )
    }

    fun checkTeam(team: TeamCheckRequest) {
        disposables.add(apiUseCase.checkTeam(team)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> teamCheck.value = result },
                        { e -> exception.value = e })
        )
    }

    fun getTeam(id: Int) {
        disposables.add(apiUseCase.getTeam(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> team.value = result },
                        { e -> exception.value = e })
        )
    }

    fun updateTeam(team: Team) {
        disposables.add(apiUseCase.updateTeam(team.id, team)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> teamUpdate.value = result },
                        { e -> exception.value = e })
        )
    }

    fun getTeamUsers(id: Int) {
        disposables.add(apiUseCase.getTeamUsers(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ result -> teamUsers.value = result },
                        { e -> exception.value = e })
        )
    }

    fun addUserToTeam(id: Int, teamUser: TeamUser) {
        disposables.add(apiUseCase.addUserToTeam(id, teamUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({ },
                        { e -> exception.value = e })
        )
    }

    fun removeUserFromTeam(id: Int, teamUser: TeamUser) {
        disposables.add(apiUseCase.removeUserFromTeam(id, teamUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({
                    loadingStatus.value = true
                })
                .doOnNext({
                    loadingStatus.value = false
                })
                .subscribe({  },
                        { e -> exception.value = e })
        )
    }
}