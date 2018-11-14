package io.liveui.boost.api.service

import io.liveui.boost.api.model.*
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.*

interface BoostApiService {

    @GET
    fun filter(@Url url: String,
               @Query("name") name: String? = null,
               @Query("info") info: String? = null,
               @Query("platform") platform: String? = null,
               @Query("identifier") identifier: String? = null,
               @Query("build") build: Int? = null,
               @Query("version") version: String? = null,
               @Query("limit") limit: Int? = null,
               @Query("page") page: Int? = null): Observable<MutableList<App>>

    @Multipart
    @POST
    fun upload(@Url url: String, @Query("tags") tags: String): Observable<App>

    @GET
    fun getApps(@Url url: String): Observable<MutableList<App>>

    @GET
    fun getApp(@Url url: String): Observable<App>

    @PUT
    fun updateApp(@Url url: String, @Body app: App): Observable<App>

    @DELETE
    fun deleteApp(@Url url: String): Observable<App>

    @GET
    fun appsOverview(@Url url: String): Observable<MutableList<AppOverview>>

    @GET
    fun getUploadTokensForUser(@Url url: String): Observable<MutableList<User>>

    @GET
    fun getUploadKey(@Url url: String): Observable<MutableList<User>>

    @POST
    fun updateUploadKey(@Url url: String): Observable<MutableList<User>>

    @DELETE
    fun deleteUploadKey(@Url url: String): Observable<MutableList<User>>

    @GET
    fun getTeams(@Url url: String): Observable<MutableList<Team>>

    @POST
    fun createTeam(@Url url: String, @Body team: CreateTeamRequest): Observable<Team>

    @POST
    fun checkTeam(@Url url: String, @Body team: TeamCheckRequest): Observable<TeamCheckResponse>

    @GET
    fun getTeam(@Url url: String): Observable<Team>

    @PUT
    fun updateTeam(@Url url: String, @Body team: Team): Observable<Team>

    @GET
    fun getTeamUsers(@Url url: String): Observable<MutableList<User>>

    @POST
    fun addUserToTeam(@Url url: String, @Body user: User): Completable

    @POST
    fun removeUserFromTeam(@Url url: String, @Body user: User): Completable

    @GET
    fun getUploadTokensForTeam(@Url url: String): Observable<MutableList<Team>>

    @POST
    fun createUploadTokenInTeam(@Url url: String, @Body team: Team): Observable<Team>

    @GET
    fun teamAppsOverview(@Url url: String): Observable<MutableList<AppOverview>>

    @GET
    fun getTeamInfo(@Url url: String): Observable<TeamInfo>

    @GET
    fun getSettings(@Url url: String): Observable<MutableList<Settings>>

    @PUT
    fun addSettings(@Url url: String): Observable<Settings>

    @DELETE
    fun deleteSettings(@Url url: String): Observable<Settings>

    @GET
    fun getUsers(@Url url: String): Observable<MutableList<User>>

    @GET
    fun getUsersGlobal(@Url url: String): Observable<MutableList<User>>

    @GET
    fun getDownloadToken(@Url url: String): Observable<AppTokenResponse>

}