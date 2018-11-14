package io.liveui.boost.ui.login

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.EXTRA_WORKSPACE
import io.liveui.boost.R
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.vmfactory.AuthViewModelFactory
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.MainActivity
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : BoostFragment() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    lateinit var authModel: LoginViewModel

    @Inject
    lateinit var userSession: UserSession

    var workspace: Workspace? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        workspace = arguments?.getParcelable(EXTRA_WORKSPACE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authModel = ViewModelProviders.of(activity!!, authViewModelFactory).get(LoginViewModel::class.java)
        authModel.workspace = workspace
        authModel.auth.observe(this, Observer {
            startActivity(Intent(activity, MainActivity::class.java))
        })

        authModel.loadingStatus.observe(this, ProgressViewObserver(progressBar))
        authModel.loadingStatus.observe(this, ProgressViewObserver(login_form, false))
        authModel.exception.observe(this, Observer<Throwable> {
            Timber.e(it)
            password.text = null
        })

        btn_login.setOnClickListener {
            it.hideKeyboard(activity?.currentFocus)
            authModel.auth(username.text.toString(), password.text.toString())
        }
    }
}