package cz.mangoweb.appstore

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

}
