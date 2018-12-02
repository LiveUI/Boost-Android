package io.liveui.boost.util

import io.liveui.boost.download.FileInfo
import io.reactivex.Completable
import io.reactivex.Observable
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject

class IOUtil @Inject constructor(val gsonUtil: GsonUtil,
                                 val contextProvider: ContextProvider,
                                 val storageProvider: StorageProvider) {

    fun saveJsonObjectToFile(obj: Any, dest: File, fileName: String): Completable {
        return Completable.fromCallable {
            val json = gsonUtil.get().toJson(obj)

            File(dest, "$fileName.json").apply {
                Timber.d("FileInfo: $json")
                writeText(json)
            }
        }
    }

    fun <T> loadJsonObjectFromFile(dest: File, fileName: String, clazz: Class<T>): Observable<T> {
        return Observable.fromCallable {
            val file = File(dest, fileName)
            if (!file.exists()) {
                throw FileNotFoundException()
            }
            val json = file.readText()
            gsonUtil.get().fromJson(json, clazz)
        }
    }

    fun isFileExist(dest: File, fileName: String, @FileType suffix: String): Boolean {
        return File(dest, fileName + suffix).exists()
    }

    fun getApkInfoFile(appId: String): Observable<FileInfo> {
        return loadJsonObjectFromFile(storageProvider.getExternalDownloadFolder(), appId + JSON, FileInfo::class.java)
    }

    fun isApkFileExist(appId: String): Observable<Boolean> {
        return getApkInfoFile(appId).flatMap { fileInfo: FileInfo? ->
            return@flatMap fileInfo?.uri?.let {
                Observable.just(contextProvider.app.contentResolver.openFileDescriptor(it, "r")?.fileDescriptor?.valid() == true)
            } ?: Observable.just(false)
        }
    }

}