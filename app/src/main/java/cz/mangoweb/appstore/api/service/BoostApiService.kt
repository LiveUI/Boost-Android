package cz.mangoweb.appstore.api.service

import cz.mangoweb.appstore.api.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface BoostApiService {

    @GET("users")
    fun getUsers(): Observable<MutableList<User>>

    @POST("users")
    fun registerUser(@Body user: RegisterUser): Observable<RegisterUserResponse>

    @GET("teams")
    fun getTeams(): Observable<MutableList<Team>>

    @POST("teams")
    fun createTeam(@Body team: CreateTeamRequest): Observable<Team>

    @POST("teams/check")
    fun checkTeam(@Body team: TeamCheckRequest): Observable<TeamCheckResponse>

    @GET("teams/{id}")
    fun getTeam(@Path("id") id: Int): Observable<Team>

    @PUT("teams/{id}")
    fun updateTeam(@Path("id") id: Int, @Body team: Team): Observable<Team>

    @GET("teams/{id}/users")
    fun getTeamUsers(@Path("id") id: Int): Observable<MutableList<TeamUser>>

    @POST("teams/{id}/link")
    fun addUserToTeam(@Path("id") id: Int, @Body user: TeamUser): Observable<EmptyResponse>

    @POST("teams/{id}/unlink")
    fun removeUserFromTeam(@Path("id") id: Int, @Body user: TeamUser): Observable<EmptyResponse>

    @GET("overview?sort={name}&filter={filter}&platform={platform}")
    fun filter(@Path("") name: String, @Path("filter") filter: String, @Path("platform") platform: String): Observable<MutableList<App>>

    @Multipart
    @POST("apps?tags={tags}")
    fun upload(@Path("tags") tags: String): Observable<App>

    @GET("apps/{platform}/{identifier}")
    fun getApps(@Path("platform") platform: String, @Path("identifier") identifier: String): Observable<MutableList<App>>

    @GET("apps/{id}?depth={level}")
    fun getApp(@Path("id") id: Int, @Path("level") level: Int): Observable<App>

    @GET("apps/{id}")
    fun getAppDetail(@Path("id") id: Int): Observable<App>

    @PUT("apps/{id}")
    fun updateApp(@Path("id") id: Int, @Body app: App): Observable<App>

    @DELETE("apps/{id}")
    fun deleteApp(@Path("id") id: Int): Observable<App>

    @GET("apps")
    fun getAppBuilds(): Observable<MutableList<App>>

    @GET("teams/{teamId}/keys")
    fun getUploadTokensForTeam(@Path("teamId") teamId: String): Observable<MutableList<Team>>

    @POST("teams/{teamId}/keys")
    fun createUploadTokenInTeam(@Path("teamId") teamId: String, @Body team: Team): Observable<Team>

    @GET("keys")
    fun getUploadTokensForUser(): Observable<MutableList<TeamUser>>

    @GET("keys/{id}")
    fun getUploadKey(@Path("id") id: String): Observable<MutableList<TeamUser>>

    @POST("keys/{id}")
    fun updateUploadKey(@Path("id") id: String): Observable<MutableList<TeamUser>>

    @DELETE("keys/{id}")
    fun deleteUploadKey(@Path("id") id: String): Observable<MutableList<TeamUser>>

}