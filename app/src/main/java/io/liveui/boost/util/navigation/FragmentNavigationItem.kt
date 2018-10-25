package io.liveui.boost.util.navigation

import android.os.Bundle
import io.liveui.boost.ui.BoostFragment

class FragmentNavigationItem(val clazz: Class<out BoostFragment>,
                             val args: Bundle? = null,
                             val addToBackStack: Boolean = false,
                             val backStackName: String? = null,
                             val reorderingAllowed: Boolean = true,
                             val transitionOptions: TransitionOptions? = null) {
    
    fun getFragment(): BoostFragment {
        val fragment = clazz.newInstance()
        return with(fragment) {
            arguments = args
            sharedElementEnterTransition = transitionOptions?.enterSheredElementTransition
            sharedElementReturnTransition = transitionOptions?.returnSharedElementTransition
            enterTransition = transitionOptions?.enterTransition
            exitTransition = transitionOptions?.exitTransition
            returnTransition = transitionOptions?.returnTransition
            reenterTransition = transitionOptions?.reenterTransition
            this
        }
    }
}