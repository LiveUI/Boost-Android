package io.liveui.boost.api.usecase

import io.liveui.boost.api.model.*
import io.liveui.boost.api.service.BoostCheckService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BoostCheckUseCase @Inject constructor(private val checkService: BoostCheckService) {

    fun ping(url: String): Observable<Message> {
        return checkService.ping(url.let {
            if (!it.endsWith("/")) {
                it.plus("/")
            }
            it.plus("ping")
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun teapot(): Observable<Message> {
        return checkService.teapot().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    fun getInfo(url: String): Observable<ServerInfo> {
        return checkService.getInfo(url.let {
            if (!it.endsWith("/")) {
                it.plus("/")
            }
            it.plus("info")
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}