package io.liveui.boost.api

import android.os.Environment
import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.usecase.BoostDownloadUseCase
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import okhttp3.Headers
import okhttp3.ResponseBody
import okio.Okio
import okio.Source
import retrofit2.Response
import java.io.File
import java.io.IOException


class DownloadManager constructor(val downloadUseCase: BoostDownloadUseCase) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val baseDir: File

    init {
        baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile
    }

    fun downloadApp(appId: String) {
        disposables.add(downloadUseCase.getDownloadToken(appId)
                .flatMap { response: AppTokenResponse ->
                    return@flatMap downloadUseCase.downloadApp(appId, response.download_token)
                }.flatMap { responseBody: Response<ResponseBody> ->
                    val publisher: PublishSubject<File> = PublishSubject.create()
                    val file = File(baseDir, getFileName(responseBody.headers()))
                    saveFile(file, responseBody.body()!!.source())
                    publisher.onNext(file)
                    return@flatMap publisher
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ },
                        { }))

    }

    fun getFileName(headers: Headers?): String {
        val header = headers?.get("Content-Disposition")
        val fileName = header?.replace("attachment; filename=", "")
        return fileName ?: "unknown-file"
    }

    @Throws(IOException::class)
    fun saveFile(file: File, source: Source) {
        val sink = Okio.buffer(Okio.sink(file))
        sink.writeAll(source)
        sink.close()
    }

    class DownloadInfo(val appId: String, val token: String, timestamp: Long) {

        enum class Status {
            IN_PROGRESS,
            COMPLETE,
            PAUSED
        }

    }
}