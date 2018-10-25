package io.liveui.boost.util.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader

inline fun <reified T> Gson.genericFromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

inline fun <reified T> Gson.genericFromJson(reader: Reader) = this.fromJson<T>(reader, object: TypeToken<T>() {}.type)

inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

fun getGson(): Gson {
    return Gson()
}

fun toJson(obj: Any): String {
    return getGson().toJson(obj)
}

fun <T> fromJson(json: String, clazz: Class<T>): T {
    return getGson().fromJson(json, clazz)
}