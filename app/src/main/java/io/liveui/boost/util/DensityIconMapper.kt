package io.liveui.boost.util

import javax.inject.Inject

class DensityIconMapper @Inject constructor(val resourcesProvider: ResourcesProvider) {

    /**
     * return icon for density values 1.0 - 16, 1.5 - 64, 2.0 - 128, 3.0 - 192, 4.0 - 256 if density doesn't match return default value (512)
     */
    fun getValueForDensity(density: Float = resourcesProvider.getDisplayDensity()): Int {
        return when {
            density >= 6f -> 512
            density >= 4f -> 256
            density >= 3f -> 192
            density >= 2f -> 128
            density >= 1.5f -> 64
            else -> 16
        }
    }
}