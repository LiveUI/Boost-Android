package io.liveui.boost.ui

import io.liveui.boost.util.navigation.FragmentNavigationItem

sealed class UiState {
    class Error(val e: Throwable) : UiState()
    class Success(val navigationItem: FragmentNavigationItem) : UiState()
}