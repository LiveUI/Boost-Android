package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.service.BoostDownloadService
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class BoostDownloadUseCase @Inject constructor(private val downloadService: BoostDownloadService) {

    fun getDownloadToken(id: String): Observable<AppTokenResponse> {
        return downloadService.getDownloadToken(id)
    }

    fun downloadApp(id: String, token: String): Observable<Response<ResponseBody>> {
        return downloadService.downloadApp(id, token)
    }

    fun getPropertyListFile(token: String): Observable<Response<ResponseBody>> {
        return downloadService.getPropertyListFile(token)
    }

}