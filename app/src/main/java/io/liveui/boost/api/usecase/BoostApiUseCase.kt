package io.liveui.boost.api.usecase

import androidx.annotation.StringDef
import io.liveui.boost.api.model.*
import io.liveui.boost.api.service.BoostApiService
import io.liveui.boost.util.UrlProvider
import io.liveui.boost.util.ext.path
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//TODO rewrite to interface with annotations
class BoostApiUseCase @Inject constructor(private val apiService: BoostApiService,
                                          urlProvider: UrlProvider) {

    private var serverUrl = urlProvider.getUrl()

    fun filter(name: String? = null,
               info: String? = null,
               platform: String? = null,
               identifier: String? = null,
               build: Int? = null,
               version: String? = null,
               limit: Int? = null,
               page: Int? = null): Observable<MutableList<App>> {
        return apiService.filter(serverUrl.path(APPS), name, info, platform, identifier, build, version, limit, page)
    }

    fun upload(tags: String): Observable<App> {
        return apiService.upload(serverUrl.path(APPS), tags)
    }

    fun getApps(): Observable<MutableList<App>> {
        return apiService.getApps(serverUrl.path(APPS))
    }

    fun getApp(id: String): Observable<App> {
        return apiService.getApp(serverUrl.path(APPS_ID, "id", id)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun updateApp(id: String, app: App): Observable<App> {
        return apiService.updateApp(serverUrl.path(APPS_ID, "id", id), app)
    }

    fun deleteApp(id: String): Observable<App> {
        return apiService.deleteApp(serverUrl.path(APPS_ID, "id", id))
    }

    fun appsOverview(): Observable<MutableList<AppOverview>> {
        return apiService.appsOverview(serverUrl.path(APPS_OVERVIEW))
    }

    fun getUploadTokensForUser(): Observable<MutableList<User>> {
        return apiService.getUploadTokensForUser(serverUrl.path(KEYS))
    }

    fun getUploadKey(id: String): Observable<MutableList<User>> {
        return apiService.getUploadKey(serverUrl.path(KEYS_ID, "id", id))
    }

    fun updateUploadKey(id: String): Observable<MutableList<User>> {
        return apiService.updateUploadKey(serverUrl.path(KEYS_ID, "id", id))
    }

    fun deleteUploadKey(id: String): Observable<MutableList<User>> {
        return apiService.deleteUploadKey(serverUrl.path(KEYS_ID, "id", id))
    }

    fun getTeams(): Observable<MutableList<Team>> {
        return apiService.getTeams(serverUrl.path(TEAMS))
    }

    fun createTeam(team: CreateTeamRequest): Observable<Team> {
        return apiService.createTeam(serverUrl.path(TEAMS), team)
    }

    fun checkTeam(team: TeamCheckRequest): Observable<TeamCheckResponse> {
        return apiService.checkTeam(serverUrl.path(TEAMS_CHECK), team)
    }

    fun getTeam(id: String): Observable<Team> {
        return apiService.getTeam(serverUrl.path(TEAMS_TEAMID, "teamId", id))
    }

    fun updateTeam(id: String, team: Team): Observable<Team> {
        return apiService.updateTeam(serverUrl.path(TEAMS_TEAMID, "teamId", id), team)
    }

    fun getTeamUsers(id: String): Observable<MutableList<User>> {
        return apiService.getTeamUsers(serverUrl.path(TEAMS_TEAMID_USERS, "teamId", id))
    }

    fun addUserToTeam(id: String, user: User): Completable {
        return apiService.addUserToTeam(serverUrl.path(TEAMS_TEAMID_LINK, "teamId", id), user)
    }

    fun removeUserFromTeam(id: String, user: User): Completable {
        return apiService.removeUserFromTeam(serverUrl.path(TEAMS_TEAMID_UNLINK, "teamId", id), user)
    }

    fun getUploadTokensForTeam(teamId: String): Observable<MutableList<Team>> {
        return apiService.getUploadTokensForTeam(serverUrl.path(TEAMS_TEAMID_KEYS, "teamId", teamId))
    }

    fun createUploadTokenInTeam(teamId: String, team: Team): Observable<Team> {
        return apiService.createUploadTokenInTeam(serverUrl.path(TEAMS_TEAMID_KEYS, "teamId", teamId), team)
    }

    fun teamAppsOverview(id: String): Observable<MutableList<AppOverview>> {
        return apiService.teamAppsOverview(serverUrl.path(TEAMS_TEAMID_APPS_OVERVIEW, "teamId", id))
    }

    fun getTeamInfo(id: String): Observable<TeamInfo> {
        return apiService.getTeamInfo(serverUrl.path(TEAMS_TEAMID_APPS_INFO, "teamId", id))
    }

    fun getSettings(): Observable<MutableList<Settings>> {
        return apiService.getSettings(serverUrl.path(SETTINGS))
    }

    fun addSettings(id: String): Observable<Settings> {
        return apiService.addSettings(serverUrl.path(SETTINGS_ID, "id", id))
    }

    fun deleteSettings(id: String): Observable<Settings> {
        return apiService.deleteSettings(serverUrl.path(SETTINGS_ID, "id", id))
    }

    fun getUsers(): Observable<MutableList<User>> {
        return apiService.getUsers(serverUrl.path(USERS))
    }

    fun getUsersGlobal(): Observable<MutableList<User>> {
        return apiService.getUsersGlobal(serverUrl.path(USERS_GLOBAL))
    }

    fun getDownloadToken(id: String): Observable<AppTokenResponse> {
        return apiService.getDownloadToken(serverUrl.path(APPS_ID_AUTH, "id", id))
    }

    companion object {
        @StringDef(APPS, APPS_ID, APPS_OVERVIEW, KEYS, KEYS_ID, TEAMS, TEAMS_CHECK, TEAMS_TEAMID,
                TEAMS_TEAMID_USERS, TEAMS_TEAMID_LINK, TEAMS_TEAMID_UNLINK, TEAMS_TEAMID_KEYS,
                TEAMS_TEAMID_APPS_OVERVIEW, TEAMS_TEAMID_APPS_INFO, SETTINGS, SETTINGS_ID, USERS,
                USERS_GLOBAL, APPS_ID_AUTH)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ENDPOINT

        const val APPS = "apps"
        const val APPS_ID = "apps/{id}"
        const val APPS_OVERVIEW = "apps/overview"
        const val KEYS = "keys"
        const val KEYS_ID = "keys/{id}"
        const val TEAMS = "teams"
        const val TEAMS_CHECK = "teams/check"
        const val TEAMS_TEAMID = "teams/{teamId}"
        const val TEAMS_TEAMID_USERS = "teams/{teamId}/users"
        const val TEAMS_TEAMID_LINK = "teams/{teamId}/link"
        const val TEAMS_TEAMID_UNLINK = "teams/{teamId}/unlink"
        const val TEAMS_TEAMID_KEYS = "teams/{teamId}/keys"
        const val TEAMS_TEAMID_APPS_OVERVIEW = "teams/{teamId}/apps/overview"
        const val TEAMS_TEAMID_APPS_INFO = "teams/{teamId}/apps/info"
        const val SETTINGS = "settings"
        const val SETTINGS_ID = "settings/{id}"
        const val USERS = "users"
        const val USERS_GLOBAL = "users/global"
        const val APPS_ID_AUTH = "apps/{id}/auth"
    }
}