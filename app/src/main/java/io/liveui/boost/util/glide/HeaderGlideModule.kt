package io.liveui.boost.util.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import io.liveui.boost.AppStoreApplication
import io.liveui.boost.di.GLIDE_CLIENT
import okhttp3.OkHttpClient
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Named

@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class HeaderGlideModule : AppGlideModule() {

    @field:[Inject Named(GLIDE_CLIENT)]
    lateinit var okClient: OkHttpClient

    init {
        AppStoreApplication.component
                .glideComponentBuilder()
                .build()
                .inject(this)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okClient))
    }

    override fun isManifestParsingEnabled() = false
}