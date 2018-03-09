package cz.mangoweb.appstore.ui.splash

import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.ui.BoostActivity

class SplashActivity : BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}
