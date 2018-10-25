package io.liveui.boost.util.glide

import dagger.Subcomponent
import io.liveui.boost.di.AppModule

@Subcomponent
interface GlideComponent {
    fun inject(newOkHttpLibraryGlideModule: HeaderGlideModule)

    @Subcomponent.Builder
    interface Builder {

        fun build(): GlideComponent
    }
}