package cz.mangoweb.appstore.api

import android.app.Application
import cz.mangoweb.appstore.BuildConfig
import okhttp3.Request
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Vojtech Hrdina on 05/03/2018.
 */

class MockResponseResolver @Inject constructor(private val application: Application) {

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
        val url: String = request.url().toString()
        val method = request.method()
        val path: String = normalizeUrl(url, method)

        return getTextFromStream(getFileInputStream(path))
    }

    internal class UrlRule(val pattern: String, val replace: String, val pattern2: String? = null)

    fun normalizeUrl(url: String, method: String): String {

        var urlOpt = url.replace(BuildConfig.BASE_URL, "")

        for (rule in rules) {
            if(rule.pattern2 == null) {
                urlOpt = urlOpt.replace(rule.pattern, rule.replace)
            } else {
                if(Pattern.matches(rule.pattern2, url)) {
                    urlOpt = urlOpt.replace(rule.pattern, rule.replace)
                }
            }
        }
        return "$method/$urlOpt.json".replace("?", "_")
    }

}