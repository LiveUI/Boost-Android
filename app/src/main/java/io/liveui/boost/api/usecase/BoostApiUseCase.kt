package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.*
import io.liveui.boost.api.service.BoostApiService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.Query
import javax.inject.Inject

class BoostApiUseCase @Inject constructor(private val apiService: BoostApiService) {

    fun getUsers(): Observable<MutableList<User>> {
        return apiService.getUsers()
    }

    fun registerUser(user: RegisterUser): Observable<User> {
        return apiService.registerUser(user)
    }

    fun getTeams(): Observable<MutableList<Team>> {
        return apiService.getTeams()
    }

    fun createTeam(team: CreateTeamRequest): Observable<Team> {
        return apiService.createTeam(team)
    }

    fun checkTeam(team: TeamCheckRequest): Observable<TeamCheckResponse> {
        return apiService.checkTeam(team)
    }

    fun getTeam(id: String): Observable<Team> {
        return apiService.getTeam(id)
    }

    fun updateTeam(id: String, team: Team): Observable<Team> {
        return apiService.updateTeam(id, team)
    }

    fun getTeamUsers(id: String): Observable<MutableList<User>> {
        return apiService.getTeamUsers(id)
    }

    fun addUserToTeam(id: String, user: User): Completable {
        return apiService.addUserToTeam(id, user)
    }

    fun removeUserFromTeam(id: String, user: User): Completable {
        return apiService.removeUserFromTeam(id, user)
    }

    fun filter(name: String? = null,
               info: String? = null,
               platform: String? = null,
               identifier: String? = null,
               build: Int? = null,
               version: String? = null,
               limit: Int? = null,
               page: Int? = null): Observable<MutableList<App>> {
        return apiService.filter(name, info, platform, identifier, build, version, limit, page)
    }

    fun upload(tags: String): Observable<App> {
        return apiService.upload(tags)
    }

    fun getApps(): Observable<MutableList<App>> {
        return apiService.getApps()
    }

    fun getApp(id: String): Observable<App> {
        return apiService.getApp(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun updateApp(id: String, app: App): Observable<App> {
        return apiService.updateApp(id, app)
    }

    fun deleteApp(id: String): Observable<App> {
        return apiService.deleteApp(id)
    }

    fun getUploadTokensForTeam(teamId: String): Observable<MutableList<Team>> {
        return apiService.getUploadTokensForTeam(teamId)
    }

    fun createUploadTokenInTeam(teamId: String, team: Team): Observable<Team> {
        return apiService.createUploadTokenInTeam(teamId, team)
    }

    fun getUploadTokensForUser(): Observable<MutableList<User>> {
        return apiService.getUploadTokensForUser()
    }

    fun getUploadKey(id: String): Observable<MutableList<User>> {
        return apiService.getUploadKey(id)
    }

    fun updateUploadKey(id: String): Observable<MutableList<User>> {
        return apiService.updateUploadKey(id)
    }

    fun deleteUploadKey(id: String): Observable<MutableList<User>> {
        return apiService.deleteUploadKey(id)
    }

    fun appsOverview(): Observable<MutableList<AppOverview>> {
        return apiService.appsOverview()
    }

    fun teamAppsOverview(id: String): Observable<MutableList<AppOverview>> {
        return apiService.teamAppsOverview(id)
    }

    fun getTeamInfo(id: String): Observable<TeamInfo> {
        return apiService.getTeamInfo(id)
    }

    fun getSettings(): Observable<MutableList<Settings>> {
        return apiService.getSettings()
    }

    fun addSettings(id: String): Observable<Settings> {
        return apiService.addSettings(id)
    }

    fun deleteSettings(id: String): Observable<Settings> {
        return apiService.deleteSettings(id)
    }

    fun getDownloadToken(id: String): Observable<AppTokenResponse> {
        return apiService.getDownloadToken(id)
    }
}