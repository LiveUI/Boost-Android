package io.liveui.boost.ui.keys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.vmfactory.ApiViewModeFactory
import io.liveui.boost.ui.BoostFragment
import javax.inject.Inject

class ApiKeysFragment: BoostFragment() {

    @Inject
    lateinit var apiViewModeFactory: ApiViewModeFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_api_keys, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}