package cz.mangoweb.appstore.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.AuthViewModelFactory
import cz.mangoweb.appstore.api.model.AuthResponse
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    lateinit var authModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authModel = ViewModelProviders.of(this, authViewModelFactory).get(LoginViewModel::class.java)

        authModel.exception.observe(this, Observer<Throwable> {
            Timber.e(it)
            password.text = null
        })

        authModel.auth.observe(this, Observer<AuthResponse> {  })

        btnLogin.setOnClickListener({
            authModel.loadApps(username.text.toString(), password.text.toString())
        })
    }

}
