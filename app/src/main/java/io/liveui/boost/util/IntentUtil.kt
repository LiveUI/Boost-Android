package io.liveui.boost.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings


object IntentUtil {

    fun getOpenApkIntent(uri: Uri): Intent {
        return Intent().apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            data = uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                action = Intent.ACTION_INSTALL_PACKAGE
            } else {
                action = Intent.ACTION_VIEW
                type = "application/vnd.android.package-archive"
            }
        }
    }

    fun openApk(context: Context, uri: Uri) {
        context.startActivity(getOpenApkIntent(uri))
    }

    fun getShareIntent() {

    }

    fun share(context: Context) {

    }

    fun openSettings(context: Context, packageName: String) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}