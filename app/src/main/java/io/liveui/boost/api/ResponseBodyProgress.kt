package io.liveui.boost.api

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*


class ResponseBodyProgress constructor(val responseBody: ResponseBody, val observables: MutableList<Subject<Long>>) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    val progressObservable: Subject<Long> = PublishSubject.create()

    init {
        observables.add(progressObservable)
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource? {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()))
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return ProgressForwardingSource(source, observables)
    }

    class ProgressForwardingSource(source: Source, val observables: MutableList<Subject<Long>>) : ForwardingSource(source) {

        val progressObservable: Subject<Long> = PublishSubject.create()
        var totalBytesRead = 0L

        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            totalBytesRead += if (bytesRead != -1L) bytesRead else 0
            progressObservable.onNext(totalBytesRead)
            if (totalBytesRead == -1L) {
                progressObservable.onComplete()
                observables.remove(progressObservable)
            }
            return bytesRead
        }
    }


    fun getFileSize(body: ResponseBody): Long {
        return body.contentLength()
    }
}