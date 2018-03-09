package cz.mangoweb.appstore.ui.apps


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.mangoweb.appstore.R
import cz.mangoweb.appstore.ui.BoostFragment


/**
 *
 */
class AppsFragment : BoostFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_apps, container, false)
    }

}
