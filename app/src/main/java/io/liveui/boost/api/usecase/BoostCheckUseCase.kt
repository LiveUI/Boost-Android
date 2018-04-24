package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.*
import io.liveui.boost.api.service.BoostCheckService
import io.reactivex.Observable
import javax.inject.Inject

class BoostCheckUseCase @Inject constructor(private val checkService: BoostCheckService) {

    fun ping(): Observable<Message> {
        return checkService.ping()
    }

    fun teapot(): Observable<Message> {
        return checkService.teapot()
    }

    fun getInfo(): Observable<ServerInfo> {
        return checkService.getInfo()
    }
}