package io.liveui.boost.util

import okhttp3.Request


interface HeaderProvider {

    fun getHeader(): String?

    fun saveHeader(token: String): Int

    fun applyHeader(builder: Request.Builder?): Request.Builder?
}