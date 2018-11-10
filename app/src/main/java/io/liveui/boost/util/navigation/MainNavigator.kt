package io.liveui.boost.util.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.liveui.boost.util.ext.transactWithNavigationItem
import java.util.*

class MainNavigator : FragmentNavigator {

    val navigationItemStack = Stack<FragmentNavigationItem>()

    var onNavigationStackChangeListener: FragmentNavigator.OnNavigationStackChangedListener? = null

    lateinit var fragmentManager: FragmentManager

    var containerId: Int = -1

    override fun replaceFragment(navigationItem: FragmentNavigationItem) {
        fragmentManager.transactWithNavigationItem(navigationItem) {
            replace(containerId, navigationItem.getFragment())
            if (navigationItemStack.isNotEmpty()) {
                navigationItemStack.pop()
            }
            navigationItemStack.add(navigationItem)
            notifyNavigationStackChanged(true)
        }
    }

    override fun addFragment(navigationItem: FragmentNavigationItem) {
        fragmentManager.transactWithNavigationItem(navigationItem) {
            add(containerId, navigationItem.getFragment())
            navigationItemStack.add(navigationItem)
            notifyNavigationStackChanged(true)
        }
    }

    override fun popFragment(): FragmentNavigationItem? {
        return if (navigationItemStack.isNotEmpty()) {
            fragmentManager.popBackStack()
            val pop = navigationItemStack.pop()
            notifyNavigationStackChanged(false)
            pop
        } else {
            null
        }
    }

    override fun popToClass(clazz: Class<out Fragment>): FragmentNavigationItem? {
        val item = navigationItemStack.find { it.clazz == clazz } ?: return null

        navigationItemStack.asReversed().forEach {
            if (it == item) {
                fragmentManager.popBackStackImmediate()
                val pop = navigationItemStack.pop()
                notifyNavigationStackChanged(false)
                return pop
            } else {
                fragmentManager.popBackStackImmediate()
                navigationItemStack.pop()
            }
        }

        return null
    }

    override fun popStack(stackName: String?) {
        val item = navigationItemStack.find { it.backStackName == stackName } ?: return

        navigationItemStack.asReversed().forEach {
            if (it == item) {
                fragmentManager.popBackStackImmediate()
                navigationItemStack.pop()
                notifyNavigationStackChanged(false)
                return
            } else {
                fragmentManager.popBackStackImmediate()
                navigationItemStack.pop()
            }
        }

    }

    override fun clearBackStack(): Boolean {
        val stackEntryCount = fragmentManager.backStackEntryCount
        for (i in stackEntryCount downTo 0) {
            fragmentManager.popBackStackImmediate() //TODO check
        }
        navigationItemStack.clear()
        notifyNavigationStackChanged(false)
        return true
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

}