package io.liveui.boost.util.navigation

import androidx.fragment.app.Fragment
import java.util.*

interface FragmentNavigator {

    fun replaceFragment(navigationItem: FragmentNavigationItem)

    fun addFragment(navigationItem: FragmentNavigationItem)

    fun popFragment(): FragmentNavigationItem?

    fun popToClass(clazz: Class<out Fragment>): FragmentNavigationItem?

    fun popStack(stackName: String?)

    fun clearBackStack(): Boolean //Could be void

    fun getBackStack(): Stack<FragmentNavigationItem>

    fun getCurrentItem(): FragmentNavigationItem?

    interface OnNavigationStackChangedListener {
        fun onStackChanged(navigationItem: FragmentNavigationItem, added: Boolean)
    }

}