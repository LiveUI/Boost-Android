package io.liveui.boost.ui.settings

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.common.model.Settings
import io.liveui.boost.ui.BoostFragment
import io.liveui.boost.ui.teams.TeamsActivity
import io.liveui.boost.ui.teams.TeamsFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment: BoostFragment() {

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    val settingsData: MutableLiveData<MutableList<Settings>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsData.observe(this, settingsAdapter)
        settingsData.value = ArrayList<Settings>().apply {
            add(Settings(getString(R.string.settings_team), Settings.Action(TeamsActivity::class.java, null, TeamsFragment::class.java)))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = settingsAdapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        settingsAdapter.selectedItem.observe(this, Observer {

        })
    }
}