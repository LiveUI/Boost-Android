package cz.mangoweb.appstore.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.api.ApiViewModeFactory
import cz.mangoweb.appstore.api.model.RegisterUserResponse
import cz.mangoweb.appstore.ui.BoostActivity
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BoostActivity() {

    @Inject
    lateinit var apiViewModelFactory: ApiViewModeFactory

    lateinit var registerModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerModel = ViewModelProviders.of(this, apiViewModelFactory).get(RegisterViewModel::class.java)

        registerModel.register(firstName.text.toString(),
                lastName.text.toString(),
                email.text.toString(),
                password.text.toString())

        registerModel.register.observe(this, Observer<RegisterUserResponse> {

        })
    }
}
