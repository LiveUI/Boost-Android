package cz.mangoweb.appstore.api

import android.app.Application
import cz.mangoweb.appstore.AppStoreApplication
import cz.mangoweb.appstore.BuildConfig
import io.reactivex.Single
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import org.apache.commons.io.IOUtils
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */

class MockResponseResolver @Inject constructor(val application: Application) {

    private var rules: Array<UrlRule>

    init {
        rules = arrayOf(
                UrlRule(".+",  "apps", "/(android|ios)/.+"),
                UrlRule("/[0-9]+\\?",  "0"),
                UrlRule("\\=.+",  ""),
                UrlRule("/[0-9]+/", "/0/"),
                UrlRule("/[0-9]+$", "/0")
        )
    }


    @Throws(IOException::class)
    private fun getFileInputStream(filename: String): InputStream {
        return application.assets.open(filename)
    }

    @Throws(IOException::class)
    private fun getTextFromStream(inputStream: InputStream): String {
        return IOUtils.toString(inputStream, Charset.forName("UTF-8"))
    }

    @Throws(IOException::class)
    fun getMockResponse(request: Request): String {
        val url: String = request.url().toString().replace(BuildConfig.BASE_URL, "")
        val method = request.method()
        val path: String = normalizeUrl(url, method)

        return getTextFromStream(getFileInputStream(path))
    }

    internal class UrlRule(val pattern: String, val replace: String, val pattern2: String? = null)

    private fun normalizeUrl(url: String, method: String): String {
        for (rule in rules) {
            if(rule.pattern2 == null) {
                url.replace(rule.pattern, rule.replace)
            } else {
                if(Pattern.matches(rule.pattern2, url)) {
                    url.replace(rule.pattern, rule.replace)
                }
            }
        }
        return "$method/$url.json".replace("?", "_")
    }

}