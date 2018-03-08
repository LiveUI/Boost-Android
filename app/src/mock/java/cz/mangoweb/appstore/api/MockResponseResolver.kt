package cz.mangoweb.appstore.api

import android.app.Application
import cz.mangoweb.appstore.AppStoreApplication
import cz.mangoweb.appstore.BuildConfig
import io.reactivex.Single
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */

class MockResponseResolver @Inject constructor(val application: Application) {

    @Throws(IOException::class)
    fun getFileInputStream(filename: String): InputStream {
        return application.assets.open(filename)
    }

    @Throws(IOException::class)
    fun getTextFromStream(inputStream: InputStream): String {
        return IOUtils.toString(inputStream, Charset.forName("UTF-8"))
    }

    @Throws(IOException::class)
    fun getMockResponse(url: String): String {
        return getTextFromStream(getFileInputStream(url.replace(BuildConfig.BASE_URL, "").plus(".json")))
    }

}