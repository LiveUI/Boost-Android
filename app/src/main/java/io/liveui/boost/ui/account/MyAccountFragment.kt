package io.liveui.boost.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.ui.BoostFragment
import kotlinx.android.synthetic.main.fragment_my_account.*
import javax.inject.Inject

class MyAccountFragment: BoostFragment() {

    @Inject
    lateinit var userSession: UserSession

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_full_name.text = userSession.user.value?.getFullName()
        email.text = userSession.user.value?.email
    }
}