package io.liveui.boost.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import io.liveui.boost.BuildConfig
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.api.AuthViewModelFactory
import io.liveui.boost.api.model.AuthResponse
import io.liveui.boost.common.model.Workspace
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.ui.apps.AppsActivity
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.hideKeyboard
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginActivity : BoostActivity() {

    @Inject
    lateinit var userSession: UserSession

    @Inject
    lateinit var workspace: Workspace

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

        server_name.text = workspace.name ?: workspace.url

        btnLogin.setOnClickListener({
            it.hideKeyboard(currentFocus)
            authModel.auth(username.text.toString(), password.text.toString())
        })
    }
}
