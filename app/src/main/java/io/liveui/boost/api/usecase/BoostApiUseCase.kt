package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.*
import io.liveui.boost.api.service.BoostApiService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class BoostApiUseCase @Inject constructor(private val apiService: BoostApiService) {

    fun getUsers(): Observable<MutableList<User>> {
        return apiService.getUsers()
    }

    fun registerUser(user: RegisterUser): Observable<RegisterUserResponse> {
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

    fun getTeam(id: Int): Observable<Team> {
        return apiService.getTeam(id)
    }

    fun updateTeam(id: Int, team: Team): Observable<Team> {
        return apiService.updateTeam(id, team)
    }

    fun getTeamUsers(id: Int): Observable<MutableList<TeamUser>> {
        return apiService.getTeamUsers(id)
    }

    fun addUserToTeam(id: Int, user: TeamUser): Completable {
        return apiService.addUserToTeam(id, user)
    }

    fun removeUserFromTeam(id: Int, user: TeamUser): Completable {
        return apiService.removeUserFromTeam(id, user)
    }

    fun filter(name: String, filter: String, platform: String): Observable<MutableList<App>> {
        return apiService.filter(name, filter, platform)
    }

    fun upload(tags: String): Observable<App> {
        return apiService.upload(tags)
    }

    fun getApps(): Observable<MutableList<App>> {
        return apiService.getApps()
    }

    fun getApp(id: Int): Observable<App> {
        return apiService.getApp(id)
    }

    fun getAppDetail(id: Int): Observable<App> {
        return apiService.getAppDetail(id)
    }

    fun updateApp(id: Int, app: App): Observable<App> {
        return apiService.updateApp(id, app)
    }

    fun deleteApp(id: Int): Observable<App> {
        return apiService.deleteApp(id)
    }

    fun getAppBuilds(): Observable<MutableList<App>> {
        return apiService.getAppBuilds()
    }

    fun getUploadTokensForTeam(teamId: String): Observable<MutableList<Team>> {
        return apiService.getUploadTokensForTeam(teamId)
    }

    fun createUploadTokenInTeam(teamId: String, team: Team): Observable<Team> {
        return apiService.createUploadTokenInTeam(teamId, team)
    }

    fun getUploadTokensForUser(): Observable<MutableList<TeamUser>> {
        return apiService.getUploadTokensForUser()
    }

    fun getUploadKey(id: String): Observable<MutableList<TeamUser>> {
        return apiService.getUploadKey(id)
    }

    fun updateUploadKey(id: String): Observable<MutableList<TeamUser>> {
        return apiService.updateUploadKey(id)
    }

    fun deleteUploadKey(id: String): Observable<MutableList<TeamUser>> {
        return apiService.deleteUploadKey(id)
    }


}