package io.liveui.boost.ui.splash

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.SplashViewModelFactory
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.MainActivity
import io.liveui.boost.ui.login.LoginActivity
import javax.inject.Inject

class SplashActivity : BoostActivity() {

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashViewModel = ViewModelProviders.of(this, splashViewModelFactory).get(SplashViewModel::class.java)
        splashViewModel.workspaceStatus.observe(this, Observer { status ->
            when (status) {
                Workspace.Status.NEW -> {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                Workspace.Status.SERVER_VERIFIED -> {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
                Workspace.Status.ACTIVATED -> {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
            }
        })
    }

    //TODO move into viewModel
    fun loadinitialData() {

    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SplashActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }
    }
}
