package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface BoostApiService {

    @GET("apps")
    fun filter(@Query("name") name: String? = null,
               @Query("info") info: String? = null,
               @Query("platform") platform: String? = null,
               @Query("identifier") identifier: String? = null,
               @Query("build") build: Int? = null,
               @Query("version") version: String? = null,
               @Query("limit") limit: Int? = null,
               @Query("page") page: Int? = null): Observable<MutableList<App>>

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

    @GET("apps/overview")
    fun appsOverview(): Observable<MutableList<AppOverview>>

    @GET("keys")
    fun getUploadTokensForUser(): Observable<MutableList<User>>

    @GET("keys/{id}")
    fun getUploadKey(@Path("id") id: String): Observable<MutableList<User>>

    @POST("keys/{id}")
    fun updateUploadKey(@Path("id") id: String): Observable<MutableList<User>>

    @DELETE("keys/{id}")
    fun deleteUploadKey(@Path("id") id: String): Observable<MutableList<User>>

    @GET("teams")
    fun getTeams(): Observable<MutableList<Team>>

    @POST("teams")
    fun createTeam(@Body team: CreateTeamRequest): Observable<Team>

    @POST("teams/check")
    fun checkTeam(@Body team: TeamCheckRequest): Observable<TeamCheckResponse>

    @GET("teams/{teamId}")
    fun getTeam(@Path("teamId") id: String): Observable<Team>

    @PUT("teams/{teamId}")
    fun updateTeam(@Path("teamId") id: String, @Body team: Team): Observable<Team>

    @GET("teams/{teamId}/users")
    fun getTeamUsers(@Path("teamId") id: String): Observable<MutableList<User>>

    @POST("teams/{teamId}/link")
    fun addUserToTeam(@Path("teamId") id: String, @Body user: User): Completable

    @POST("teams/{teamId}/unlink")
    fun removeUserFromTeam(@Path("teamId") id: String, @Body user: User): Completable

    @GET("teams/{teamId}/keys")
    fun getUploadTokensForTeam(@Path("teamId") teamId: String): Observable<MutableList<Team>>

    @POST("teams/{teamId}/keys")
    fun createUploadTokenInTeam(@Path("teamId") teamId: String, @Body team: Team): Observable<Team>

    @GET("teams/{teamId}/apps/overview")
    fun teamAppsOverview(@Path("teamId") id: String): Observable<MutableList<AppOverview>>

    @GET("teams/{teamId}/apps/info")
    fun getTeamInfo(@Path("teamId") id: String): Observable<TeamInfo>

    @GET("settings")
    fun getSettings(): Observable<MutableList<Settings>>

    @PUT("settings/{id}")
    fun addSettings(@Path("id") id: String): Observable<Settings>

    @DELETE("settings/{id}")
    fun deleteSettings(@Path("id") id: String): Observable<Settings>

    @GET("users")
    fun getUsers(): Observable<MutableList<User>>

    @GET("users/global")
    fun getUsersGlobal(): Observable<MutableList<User>>

    @POST("users")
    fun registerUser(@Body user: RegisterUser): Observable<User>

}