package io.liveui.boost.api

import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.usecase.BoostDownloadUseCase
import io.liveui.boost.util.ext.getDownloadDir
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.ResponseBody
import okio.Okio
import retrofit2.Response
import timber.log.Timber
import java.io.File


class DownloadManager constructor(val downloadUseCase: BoostDownloadUseCase) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun downloadApp(appId: String) {
        disposables.add(downloadUseCase.getDownloadToken(appId)
                .flatMap { response: AppTokenResponse ->
                    return@flatMap downloadUseCase.downloadApp(response.token)
                }.flatMap { responseBody: Response<ResponseBody> ->
                    val publisher: PublishSubject<File> = PublishSubject.create()
                    val headers = responseBody.headers()
                    val header = headers.get("Content-Disposition")
                    val fileName = header!!.replace("attachment; filename=", "")
                    val file = File(getDownloadDir(), fileName)
                    Okio.buffer(Okio.sink(file)).use {
                        it.writeAll(responseBody.body()!!.source())
                    }
                    publisher.onNext(file)
                    return@flatMap publisher
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d("File downloaded - " + it.name)
                },
                        {
                            Timber.e(it, "Download failed")
                        }))

    }

}