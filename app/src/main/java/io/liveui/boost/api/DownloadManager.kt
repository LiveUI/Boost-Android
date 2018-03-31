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
import java.io.File


class DownloadManager constructor(val downloadUseCase: BoostDownloadUseCase) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun downloadApp(appId: String) {
        disposables.add(downloadUseCase.getDownloadToken(appId)
                .flatMap { response: AppTokenResponse ->
                    return@flatMap downloadUseCase.downloadApp(appId, response.download_token)
                }.flatMap { responseBody: Response<ResponseBody> ->
                    val publisher: PublishSubject<File> = PublishSubject.create()
                    val headers = responseBody.headers()
                    val header = headers.get("Content-Disposition")
                    val fileName = header!!.replace("attachment; filename=", "")
                    val file = File(getDownloadDir(), fileName)
                    val sink = Okio.buffer(Okio.sink(file))
                    sink.writeAll(responseBody.body()!!.source())
                    sink.close()
                    publisher.onNext(file)
                    return@flatMap publisher
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ },
                        { }))

    }

}