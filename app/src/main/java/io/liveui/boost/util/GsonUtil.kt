package io.liveui.boost.util

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.liveui.boost.util.gson.DateDeserializer
import io.liveui.boost.util.gson.UriDeserializer
import java.util.*
import javax.inject.Inject

class GsonUtil @Inject constructor(val gsonBuilder: GsonBuilder) {

    init {
        gsonBuilder.registerTypeHierarchyAdapter(Calendar::class.java, DateDeserializer())
        gsonBuilder.registerTypeHierarchyAdapter(Uri::class.java, UriDeserializer())
    }

    fun get(): Gson = gsonBuilder.create()
}