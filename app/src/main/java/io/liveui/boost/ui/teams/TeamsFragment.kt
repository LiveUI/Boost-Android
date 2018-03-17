package io.liveui.boost.ui.teams


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.liveui.boost.R
import io.liveui.boost.ui.BoostFragment


/**
 *
 */
class TeamsFragment : BoostFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

}
