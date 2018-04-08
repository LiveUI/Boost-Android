package io.liveui.boost.util.ext

import com.google.gson.Gson

fun getGson(): Gson {
    return Gson()
}

fun toJson(obj: Any): String {
    return getGson().toJson(obj)
}

fun <T> fromJson(json: String, clazz: Class<T>): T {
    return getGson().fromJson(json, clazz)
}