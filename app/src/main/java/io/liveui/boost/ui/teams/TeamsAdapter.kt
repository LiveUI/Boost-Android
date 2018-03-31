package io.liveui.boost.ui.teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.Team
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_teams.view.*

class TeamsAdapter: BaseObservableAdapter<Team, TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_teams, parent, false), this)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
       holder.setData(items[position])
    }
}

class TeamViewHolder(itemView: View, onClickListener: OnItemClickListener?) : BaseViewHolder<Team>(itemView, onClickListener) {

    override fun setData(item: Team) {
        itemView.text1.text = item.name
    }

}