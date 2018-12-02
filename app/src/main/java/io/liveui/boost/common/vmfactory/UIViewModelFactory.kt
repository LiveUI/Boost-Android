package io.liveui.boost.common.vmfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.liveui.boost.ui.NavigationViewModel
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.UiActivityViewModel
import javax.inject.Inject
import javax.inject.Provider


class UIViewModelFactory @Inject constructor(val navigationViewModel: Provider<NavigationViewModel>,
                                             val toolbarViewModel: Provider<ToolbarViewModel>,
                                             val uiActivityViewModel: Provider<UiActivityViewModel>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(ToolbarViewModel::class.java) ->
                    toolbarViewModel.get()
                isAssignableFrom(NavigationViewModel::class.java) ->
                    navigationViewModel.get()
                isAssignableFrom(UiActivityViewModel::class.java) ->
                    uiActivityViewModel.get()
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            } as T
        }
    }
}