package cz.mangoweb.appstore.api.usecase

import cz.mangoweb.appstore.api.model.*
import cz.mangoweb.appstore.api.service.BoostApiService
import io.reactivex.Observable
import javax.inject.Inject

class BoostApiUseCase @Inject constructor(private val apiService: BoostApiService) {

    fun getTeams(): Observable<List<Team>> {
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

    fun getTeamUsers(id: Int): Observable<List<TeamUser>> {
        return apiService.getTeamUsers(id)
    }

    fun addUserToTeam(id: Int, user: TeamUser): Observable<EmptyResponse> {
        return apiService.addUserToTeam(id, user)
    }

    fun removeUserFromTeam(id: Int, user: TeamUser): Observable<EmptyResponse> {
        return apiService.removeUserFromTeam(id, user)
    }

    fun filter(name: String, filter: String, platform: String): Observable<List<App>> {
        return apiService.filter(name, filter, platform)
    }

    fun upload(tags: String): Observable<App> {
        return apiService.upload(tags)
    }

    fun getApps(platform: String, identifier: String): Observable<List<App>> {
        return apiService.getApps(platform, identifier)
    }

    fun getApp(id: Int, level: Int): Observable<App> {
        return apiService.getApp(id, level)
    }

}