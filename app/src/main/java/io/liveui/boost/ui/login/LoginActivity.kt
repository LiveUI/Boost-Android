package io.liveui.boost.ui.login

import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.intro.ChooseServerFragment
import io.liveui.boost.util.ext.replaceFragmentInActivity

class LoginActivity : BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
