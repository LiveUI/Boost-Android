package io.liveui.boost.ui.appdetail

import android.os.Bundle
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.ext.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_app_detail.*

class AppDetailActivity : BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragmentInActivity(AppDetailFragment(), R.id.fragment_container)
    }
}
