package io.liveui.boost.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.liveui.boost.EXTRA_WORKSPACE
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.AuthViewModelFactory
import io.liveui.boost.common.vmfactory.UIViewModelFactory
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.MainActivity
import io.liveui.boost.ui.ToolbarViewModel
import io.liveui.boost.ui.UiActivityViewModel
import io.liveui.boost.util.ProgressViewObserver
import io.liveui.boost.util.ext.hideKeyboard
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : BoostFragment() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    @Inject
    lateinit var uiViewModelFactory: UIViewModelFactory

    private lateinit var authModel: LoginViewModel

    private lateinit var uiActivityViewModel: UiActivityViewModel

    private lateinit var toolbarViewModel: ToolbarViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.let {
            authModel = ViewModelProviders.of(it, authViewModelFactory).get(LoginViewModel::class.java).apply {
                workspace = arguments?.getParcelable(EXTRA_WORKSPACE)
            }
            toolbarViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(ToolbarViewModel::class.java)
            uiActivityViewModel = ViewModelProviders.of(it, uiViewModelFactory).get(UiActivityViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiActivityViewModel.background.postValue(R.drawable.bg_main)
        toolbarViewModel.show.postValue(View.GONE)

        authModel.auth.observe(this, Observer {
            startActivity(Intent(activity, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
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