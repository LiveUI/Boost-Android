package cz.mangoweb.appstore.api.service

import cz.mangoweb.appstore.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostApiService {

    @GET("teams")
    fun getTeams(): Observable<List<Team>>

    @POST("teams")
    fun createTeam(@Body team: CreateTeamRequest): Observable<Team>

    @POST("teams/check")
    fun checkTeam(@Body team: TeamCheckRequest): Observable<TeamCheckResponse>

    @GET("teams/{id}")
    fun getTeam(@Path("id") id: Int): Observable<Team>

    @PUT("teams/{id}")
    fun updateTeam(@Path("id") id: Int, @Body team: Team): Observable<Team>

    @GET("overview?sort={name}&filter={filter}&platform={platform}")
    fun filter(@Path("") name: String, @Path("filter") filter: String, @Path("platform") platform: String): Observable<List<App>>

    @Multipart
    @POST("upload?tags={tags}")
    fun upload(@Path("tags") tags: String): Observable<App>

    @GET("apps/{platform}/{identifier}")
    fun getApps(@Path("platform") platform: String, @Path("identifier") identifier: String): Observable<List<App>>

    @GET("apps/{id}?depth={level}")
    fun getApp(@Path("id") id: String, @Path("level") level: Int): Observable<App>


}