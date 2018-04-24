package io.liveui.boost.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.User
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_team_user.view.*

class TeamUserAdapter: BaseObservableAdapter<User, TeamUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamUserViewHolder {
        return TeamUserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_team_user, parent, false), this)
    }

    override fun onBindViewHolder(holder: TeamUserViewHolder, position: Int) {
       holder.setData(items[position])
    }
}

class TeamUserViewHolder(itemView: View, onClickListener: OnItemClickListener?) : BaseViewHolder<User>(itemView, onClickListener) {

    override fun setData(item: User) {
        itemView.name.text = item.getFullName()
        itemView.email.text = item.email
    }

}