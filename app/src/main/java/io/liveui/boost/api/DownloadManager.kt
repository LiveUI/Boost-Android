package io.liveui.boost.api

import android.app.IntentService
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.os.Environment
import io.liveui.boost.api.model.AppTokenResponse
import io.liveui.boost.api.usecase.BoostDownloadUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.os.Environment.DIRECTORY_MUSIC
import android.os.Environment.getExternalStoragePublicDirectory
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.Headers
import okhttp3.ResponseBody
import okio.*
import retrofit2.Response
import java.io.File
import java.io.IOException


class DownloadManager {

    @Inject
    private lateinit var downloadUseCase: BoostDownloadUseCase

    private val disposables: CompositeDisposable = CompositeDisposable()

    private val baseDir: File;

    init {
        baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absoluteFile
    }

    fun downloadApp(appId: String) {
        disposables.add(downloadUseCase.getDownloadToken(appId).flatMap { response: AppTokenResponse ->
            return@flatMap downloadUseCase.downloadApp(appId, response.download_token)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ },
                        { }))

    }

    fun getFileName(headers: Headers?): String {
        val header = headers?.get("Content-Disposition")
        val fileName = header?.replace("attachment; filename=", "")
        return fileName ?: "unknown-file"
    }

    fun getFileSize(body: ResponseBody): Long {
        return body.contentLength()
    }

    class ProgressForfardingSource(source: Source): ForwardingSource(source) {

        val progressObservable: Subject<Float> = PublishSubject.create()

        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            progressObservable.onNext(bytesRead.toFloat())
            if(bytesRead == -1L) progressObservable.onComplete()
            return bytesRead
        }
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