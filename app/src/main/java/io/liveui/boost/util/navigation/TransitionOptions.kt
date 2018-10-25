package io.liveui.boost.util.navigation

import android.view.View
import androidx.transition.Transition
import java.lang.ref.WeakReference

class TransitionOptions(val enterTransition: Transition?,
                        val exitTransition: Transition?,
                        val returnTransition: Transition?,
                        val reenterTransition: Transition?,
                        val enterSheredElementTransition: Transition?,
                        val returnSharedElementTransition: Transition?,
                        val sharedViews: MutableList<Pair<WeakReference<View>, String?>>?)