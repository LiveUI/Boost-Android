package io.liveui.boost.common.model

import android.content.SharedPreferences
import android.text.TextUtils
import android.webkit.URLUtil
import androidx.content.edit
import io.liveui.boost.PREF_WORKSPACE
import io.liveui.boost.util.ext.fromJson
import io.liveui.boost.util.ext.toJson

//TODO create observable name, url
class Workspace(@Transient val preferences: SharedPreferences, var name: String? = null, var url: String? = null, var permToken: String? = null) {

    fun save() {
        preferences.edit {
            putString(PREF_WORKSPACE, toJson(this@Workspace))
        }
    }

    fun load(): Boolean {
        val json = preferences.getString(PREF_WORKSPACE, null) ?: return false
        val workspace = fromJson(json, Workspace::class.java)
        name = workspace.name
        url = workspace.url
        permToken = workspace.permToken
        return true
    }

    fun clear() {
        preferences.edit {
            remove(PREF_WORKSPACE).commit()
        }
    }

    fun hasUrl(): Boolean {
        return !TextUtils.isEmpty(url) && URLUtil.isValidUrl(url)
    }

    fun hasToken(): Boolean {
        return !TextUtils.isEmpty(permToken)
    }
}