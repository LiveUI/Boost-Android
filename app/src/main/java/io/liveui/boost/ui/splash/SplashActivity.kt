package io.liveui.boost.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.login.LoginActivity

class SplashActivity : BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }, 2000)
    }
}
