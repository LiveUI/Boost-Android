package io.liveui.boost.ui.users

import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity

class UsersActivity: BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
    }
}