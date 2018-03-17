package io.liveui.boost.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.UserSession
import io.liveui.boost.api.AuthViewModelFactory
import io.liveui.boost.api.model.AuthResponse
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.util.ProgressViewObserver
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BoostActivity() {


    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    lateinit var authModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authModel = ViewModelProviders.of(this, authViewModelFactory).get(LoginViewModel::class.java)

        authModel.auth.observe(this, Observer<AuthResponse> {
            userSession.user.value = (it?.user)
            startActivity(Intent(this@LoginActivity, AppsActivity::class.java))
        })

        authModel.loadingStatus.observe(this, ProgressViewObserver(progressBar))
        authModel.loadingStatus.observe(this, ProgressViewObserver(login_form, false))

        authModel.exception.observe(this, Observer<Throwable> {
            Timber.e(it)
            password.text = null
        })

        btnLogin.setOnClickListener({
            authModel.auth(username.text.toString(), password.text.toString())
        })
    }

}
