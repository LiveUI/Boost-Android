package io.liveui.boost.ui.register

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.api.model.User
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostActivity
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

        registerModel.register.observe(this, Observer<User> {

        })
    }
}
