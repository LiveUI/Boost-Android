package io.liveui.boost.ui.appdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import io.liveui.boost.EXTRA_APP_ID
import io.liveui.boost.R
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.ext.putIntentExtras
import io.liveui.boost.util.ext.putString
import io.liveui.boost.util.ext.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_app_detail.*

class AppDetailActivity : BoostActivity() {

    companion object {
        fun startActivity(context: Context?, appId: Int?) {
            val intent = Intent(context, AppDetailActivity::class.java)
            intent.putExtra(EXTRA_APP_ID, appId)
            context?.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val appDetailFragment = AppDetailFragment()
        appDetailFragment.putIntentExtras(intent)
        replaceFragmentInActivity(appDetailFragment, R.id.fragment_container)
    }
}
