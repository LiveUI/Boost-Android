package io.liveui.boost.ui.teams

import android.content.res.ColorStateList
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.Team
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import io.liveui.boost.util.ext.getColor
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
        itemView.team_name.text = item.name
        itemView.team_identifier.text = item.name
        itemView.initials.text = item.initials
        ViewCompat.setBackgroundTintList(itemView.initials, ColorStateList.valueOf(getColor(item.color)))
    }

}