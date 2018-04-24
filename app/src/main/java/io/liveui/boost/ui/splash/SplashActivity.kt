package io.liveui.boost.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.SplashViewModelFactory
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.MainActivity
import io.liveui.boost.ui.login.LoginActivity
import io.liveui.boost.ui.workspace.add.WorkspaceAddActivity
import javax.inject.Inject

class SplashActivity : BoostActivity() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProviders.of(this, splashViewModelFactory).get(SplashViewModel::class.java)
        splashViewModel.loadData().observe(this, Observer {
            when (it) {
                null -> startActivity(Intent(this@SplashActivity, WorkspaceAddActivity::class.java))
                else -> {
                    when (it.status) {
                        Workspace.Status.SERVER_VERIFIED -> startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        Workspace.Status.ACTIVATED -> startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        else -> {
                        }
                    }
                }
            }
        })


    }
}
