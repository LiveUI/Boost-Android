package io.liveui.boost.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.workspace.WorkspaceActivity
import javax.inject.Inject

class SplashActivity : BoostActivity() {

    @Inject
    lateinit var userSession: UserSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({

            startActivity(Intent(this@SplashActivity, WorkspaceActivity::class.java))

//            userSession.activeWorkspace.observe(this@SplashActivity, Observer{
//                when {
//                    it == null -> startActivity(Intent(this@SplashActivity, WorkspaceActivity::class.java))
//                    it.hasUrl() && it.hasToken() -> startActivity(Intent(this@SplashActivity, AppsActivity::class.java))
//                    it.hasUrl() -> startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//                    else -> {
//                    }
//                }
//            })
        }, 1000)
    }
}
