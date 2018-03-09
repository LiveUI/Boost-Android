package cz.mangoweb.appstore.ui.appdetail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.ui.BoostFragment


/**
 *
 */
class AppDetailFragment : BoostFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_app_detail, container, false)
    }


}
