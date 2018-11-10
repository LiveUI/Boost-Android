package io.liveui.boost.util.glide

import android.widget.ImageView
import com.bumptech.glide.Glide
import io.liveui.boost.util.UrlProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlideProvider @Inject constructor(val urlProvider: UrlProvider) {

    fun loadAppIcon(imageView: ImageView, appId: String) {
        Glide.with(imageView).load(urlProvider.getUrl() + "apps/" + appId + "/icon").into(imageView)
    }

    fun loadAppIconFromUrl(imageView: ImageView, url: String) {
        Glide.with(imageView).load(url).into(imageView)
    }
}