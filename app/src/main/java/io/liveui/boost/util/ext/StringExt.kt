package io.liveui.boost.util.ext

import retrofit2.http.Query

fun String.path(pathSegment: String): String {
    var s = this
    if (!endsWith("/")) {
        s = s.plus("/")
    }
    s = s.plus(pathSegment)

    return s
}

fun String.path(pathSegment: String, pathKey: String, param: Any): String {
    var s = this
    if (!endsWith("/")) {
        s = s.plus("/")
    }
    s = s.plus(pathSegment)
    s = s.replace("{$pathKey}", param.toString())

    return s
}

fun String.query(value: String?): String {
    var s = this
    value?.apply {
        if (!contains("?")) {
            s = s.plus("?")
        }

        if (!endsWith("?")) {
            s = s.plus("&")
        }

        val queryKey = value.javaClass.getAnnotation(Query::class.java)?.value
        queryKey?.apply {
            s = s.plus(queryKey).plus("=").plus(this)
        }
    }

    return s
}
