package io.liveui.boost.util.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.liveui.boost.util.ext.transactWithNavigationItem
import java.lang.UnsupportedOperationException
import java.util.*

class MainNavigator : FragmentNavigator {

    val navigationItemStack = Stack<FragmentNavigationItem>()

    var onNavigationStackChangeListener: FragmentNavigator.OnNavigationStackChangedListener? = null

    var fragmentManager: FragmentManager? = null

    var containerId: Int = 0

    override fun replaceFragment(navigationItem: FragmentNavigationItem) {
        assertInit()

        fragmentManager?.apply {
            transactWithNavigationItem(navigationItem) {
                replace(containerId, navigationItem.getFragment())
                if (navigationItemStack.isNotEmpty()) {
                    navigationItemStack.pop()
                }
                navigationItemStack.add(navigationItem)
                notifyNavigationStackChanged(true)
            }
        }
    }

    override fun addFragment(navigationItem: FragmentNavigationItem) {
        assertInit()

        fragmentManager?.apply {
            transactWithNavigationItem(navigationItem) {
                add(containerId, navigationItem.getFragment())
                navigationItemStack.add(navigationItem)
                notifyNavigationStackChanged(true)
            }
        }
    }

    override fun popFragment(): FragmentNavigationItem? {
        return if (navigationItemStack.isNotEmpty()) {
            fragmentManager?.let {
                it.popBackStack()
                val pop = navigationItemStack.pop()
                notifyNavigationStackChanged(false)
                pop
            }
        } else {
            null
        }
    }

    override fun popToClass(clazz: Class<out Fragment>): FragmentNavigationItem? {
        assertInit()

        val item = navigationItemStack.find { it.clazz == clazz } ?: return null

        navigationItemStack.asReversed().forEach {
            if (it == item) {
                fragmentManager!!.popBackStackImmediate()
                val pop = navigationItemStack.pop()
                notifyNavigationStackChanged(false)
                return pop
            } else {
                fragmentManager!!.popBackStackImmediate()
                navigationItemStack.pop()
            }
        }

        return null
    }

    override fun popStack(stackName: String?) {
        assertInit()

        val item = navigationItemStack.find { it.backStackName == stackName } ?: return

        navigationItemStack.asReversed().forEach {
            if (it == item) {
                fragmentManager!!.popBackStackImmediate()
                navigationItemStack.pop()
                notifyNavigationStackChanged(false)
                return
            } else {
                fragmentManager!!.popBackStackImmediate()
                navigationItemStack.pop()
            }
        }

    }

    override fun clearBackStack(): Boolean {
       return fragmentManager?.let {
            val stackEntryCount = it.backStackEntryCount
            for (i in stackEntryCount downTo 0) {
                it.popBackStackImmediate() //TODO check
            }
            navigationItemStack.clear()
            notifyNavigationStackChanged(false)
           true
        } ?: false
    }

    override fun getBackStack(): Stack<FragmentNavigationItem> {
        return navigationItemStack
    }

    override fun getCurrentItem(): FragmentNavigationItem? {
        return navigationItemStack.peek()
    }

    private fun notifyNavigationStackChanged(added: Boolean) {
        onNavigationStackChangeListener?.onStackChanged(navigationItemStack.peek(), added)
    }

    private fun assertInit() {
        if (fragmentManager == null || containerId == 0) {
            throw UnsupportedOperationException("${if(fragmentManager == null) "FragmentManager cannot be null" else ""} ${if(containerId == 0) "Container Id is not set" else ""}")
        }
    }
}