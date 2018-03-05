package cz.mangoweb.appstore.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.AuthViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val authModel: LoginViewModel = ViewModelProviders.of(this, authViewModelFactory).get(LoginViewModel::class.java)
        authModel.loadApps("a", "a")
    }

}
