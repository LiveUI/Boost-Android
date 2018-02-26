package cz.mangoweb.appstore.api

import cz.mangoweb.appstore.api.model.*
import io.reactivex.Observable

interface BoostApi {

    fun auth(auth: AuthRequest): Observable<AuthResponse>

    fun refreshToken(): Observable<RefreshTokenResponse>

    fun getTeams(): Observable<List<Team>>

    fun createTeam(team: CreateTeamRequest): Observable<Team>

    fun checkTeam(team: TeamCheckRequest): Observable<TeamCheckResponse>

    fun getTeam(): Observable<Team>

    fun updateTeam(team: Team): Observable<Team>

    fun filter(): Observable<List<App>>

    fun upload(): Observable<App>

    fun getApps(): Observable<List<App>>

    fun getApp(): Observable<App>

}