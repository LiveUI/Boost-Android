package io.liveui.boost.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import io.liveui.boost.R
import io.liveui.boost.common.model.Workspace
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.ui.workspace.WorkspaceActivity
import javax.inject.Inject

class SplashActivity : BoostActivity() {

    @Inject
    lateinit var workspace: Workspace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            when {
                workspace.hasUrl() && workspace.hasToken() -> startActivity(Intent(this@SplashActivity, AppsActivity::class.java))
                workspace.hasUrl() -> startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                else -> {
                    startActivity(Intent(this@SplashActivity, WorkspaceActivity::class.java))
                }
            }

        }, 1000)
    }
}
