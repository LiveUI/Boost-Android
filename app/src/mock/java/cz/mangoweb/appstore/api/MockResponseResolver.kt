package cz.mangoweb.appstore.api

import cz.mangoweb.appstore.AppStoreApplication
import io.reactivex.Single
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */

class MockResponseResolver constructor(val application: AppStoreApplication) {

    @Throws(IOException::class)
    fun getFileInputStream(filename: String): InputStream {
        return application.assets.open(filename)
    }

    @Throws(IOException::class)
    fun getTextFromStream(inputStream: InputStream): String {
        return IOUtils.toString(inputStream, Charset.forName("UTF-8"))
    }

    fun loadResponse(url: String): Single<String> {

        //TODO resolve url to mock json

        return Single.defer({
            val inputStream = getFileInputStream(url)
            val text = getTextFromStream(inputStream)
            Single.just(text)
        })
    }
}