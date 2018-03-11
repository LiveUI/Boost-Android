package cz.mangoweb.appstore.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.AuthViewModelFactory
import cz.mangoweb.appstore.api.model.AuthResponse
import cz.mangoweb.appstore.ui.BoostActivity
import cz.mangoweb.appstore.ui.apps.AppStoreActivity
import cz.mangoweb.appstore.util.ProgressViewObserver
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BoostActivity() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    lateinit var authModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authModel = ViewModelProviders.of(this, authViewModelFactory).get(LoginViewModel::class.java)

        authModel.auth.observe(this, Observer<AuthResponse> {
            startActivity(Intent(this@LoginActivity, AppStoreActivity::class.java))
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
