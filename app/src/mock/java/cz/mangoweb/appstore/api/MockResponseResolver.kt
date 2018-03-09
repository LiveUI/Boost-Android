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

    private val UTF8 = Charset.forName("UTF-8")

    init {
        rules = arrayOf(
                UrlRule("auth", true, "auth.json"),
                UrlRule("token", true, "token.json"),
                UrlRule("overview\\?sort=.+", false, "overview.json"),
                UrlRule("teams/[0-9]+/unlink", true, "teams/0/unlink.json"),
                UrlRule("teams/[0-9]+/link", true, "teams/0/link.json"),
                UrlRule("teams/[0-9]+/users", false, "teams/0/users.json"),
                UrlRule("teams/[0-9]+$", true, "teams/0/updateTeam.json"),
                UrlRule("teams/[0-9]+$", false, "teams/0/getTeam.json"),
                UrlRule("teams/check", true, "teams/check.json"),
                UrlRule("teams", true, "teams/create.json"),
                UrlRule("teams", false, "teams.json")
        )
    }


    @Throws(IOException::class)
    fun getFileInputStream(filename: String): InputStream {
        return application.assets.open(filename)
    }

    @Throws(IOException::class)
    fun getTextFromStream(inputStream: InputStream): String {
        return IOUtils.toString(inputStream, Charset.forName("UTF-8"))
    }

    @Throws(IOException::class)
    fun getMockResponse(request: Request): String {
        val url: String = request.url().toString().replace(BuildConfig.BASE_URL, "")
        val body: String? = bodyToString(request.body())
        val path: String? = matchUrl(url, body)

        return if (path == null) "Unknown endpoint" else getTextFromStream(getFileInputStream(path))
    }

    private fun bodyToString(requestBody: RequestBody?): String? {
        if (requestBody == null) return null

        val buffer = Buffer()
        requestBody.writeTo(buffer)

        var charset: Charset? = UTF8
        val contentType = requestBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)
        }

        return if (isPlaintext(buffer)) {
            buffer.readString(charset!!)
        } else {
            Timber.d("Length: " + requestBody.contentLength())
            null
        }
    }

    private fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = if (buffer.size() < 64) buffer.size() else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false
        }
    }

    internal class UrlRule(private val pattern: String, private val hasBody: Boolean, private val newUlr: String) {

        fun match(url: String, body: String?): Boolean {
            return Pattern.matches(pattern, url) && (body != null && hasBody)
        }

        fun url(): String {
            return newUlr
        }
    }

    fun matchUrl(url: String, body: String?): String? {
        for (rule in rules) {
            if (rule.match(url, body)) return rule.url()
        }
        return null
    }

}