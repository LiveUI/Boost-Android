package io.liveui.boost.util.glide

import dagger.Subcomponent

@Subcomponent
interface GlideComponent {
    fun inject(newOkHttpLibraryGlideModule: HeaderGlideModule)

    @Subcomponent.Builder
    interface Builder {

        fun build(): GlideComponent
    }
}