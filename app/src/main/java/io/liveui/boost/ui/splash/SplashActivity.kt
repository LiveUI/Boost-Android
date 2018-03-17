package io.liveui.boost.ui.splash

import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity

class SplashActivity : BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}
