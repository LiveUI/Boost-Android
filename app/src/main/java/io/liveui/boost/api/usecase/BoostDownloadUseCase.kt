package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.service.BoostDownloadService
import io.reactivex.Observable
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