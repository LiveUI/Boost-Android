package io.liveui.boost.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import javax.inject.Inject

class TeamUsersFragment : BoostFragment() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    @Inject
    lateinit var adapter: TeamUserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_users, container, false)
    }
}