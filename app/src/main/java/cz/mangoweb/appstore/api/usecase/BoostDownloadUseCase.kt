package cz.mangoweb.appstore.api.usecase

import cz.mangoweb.appstore.api.model.AppTokenResponse
import cz.mangoweb.appstore.api.service.BoostDownloadService
import io.reactivex.Observable
import retrofit2.http.Path
import javax.inject.Inject

class BoostDownloadUseCase @Inject constructor(private val downloadService: BoostDownloadService) {

    fun getDownloadToken(id: Int): Observable<AppTokenResponse> {
        return downloadService.getDownloadToken(id)
    }

    fun downloadApp(id: Int, token: String) {
        return downloadService.downloadApp(id, token)
    }

    fun getPropertyListFile(token: String) {
        return downloadService.getPropertyListFile(token)
    }

}