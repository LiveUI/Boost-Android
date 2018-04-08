package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Completable
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
    fun getTeam(@Path("id") id: String): Observable<Team>

    @PUT("teams/{id}")
    fun updateTeam(@Path("id") id: String, @Body team: Team): Observable<Team>

    @GET("teams/{id}/users")
    fun getTeamUsers(@Path("id") id: String): Observable<MutableList<TeamUser>>

    @POST("teams/{id}/link")
    fun addUserToTeam(@Path("id") id: String, @Body user: TeamUser): Completable

    @POST("teams/{id}/unlink")
    fun removeUserFromTeam(@Path("id") id: String, @Body user: TeamUser): Completable

    @GET("apps")
    fun filter(@Query("sort") name: String, @Query("filter") filter: String, @Query("platform") platform: String): Observable<MutableList<App>>

    @Multipart
    @POST("apps")
    fun upload(@Query("tags") tags: String): Observable<App>

    @GET("apps")
    fun getApps(): Observable<MutableList<App>>

    @GET("apps/{id}")
    fun getApp(@Path("id") id: String): Observable<App>

    @PUT("apps/{id}")
    fun updateApp(@Path("id") id: String, @Body app: App): Observable<App>

    @DELETE("apps/{id}")
    fun deleteApp(@Path("id") id: String): Observable<App>

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