package io.liveui.boost.ui

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.util.LifecycleViewModel
import io.liveui.boost.util.navigation.MAIN_NAVIGATOR
import io.liveui.boost.util.navigation.MainNavigator
import io.liveui.boost.util.navigation.SECONDARY_NAVIGATOR
import javax.inject.Inject
import javax.inject.Named

class NavigationViewModel @Inject constructor(@param:[ActivityScope Named(MAIN_NAVIGATOR)] val mainNavigator: MainNavigator,
                                              @param:[ActivityScope Named(SECONDARY_NAVIGATOR)] val secondaryNavigator: MainNavigator) : LifecycleViewModel() {

    val mainContainerId = MutableLiveData<Int>()
    val secondaryContainerId = MutableLiveData<Int>()
    val fragmentManager = MutableLiveData<FragmentManager>()

    init {
        mainContainerId.observeForever {
            mainNavigator.containerId = it
        }

        secondaryContainerId.observeForever {
            secondaryNavigator.containerId = it
        }

        fragmentManager.observeForever {
            mainNavigator.fragmentManager = it
            secondaryNavigator.fragmentManager = it
        }
    }

}