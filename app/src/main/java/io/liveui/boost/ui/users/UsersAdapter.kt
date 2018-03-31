package io.liveui.boost.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.liveui.boost.R
import io.liveui.boost.api.model.Team
import io.liveui.boost.api.model.User
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_teams.view.*

class UsersAdapter: BaseObservableAdapter<User, UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_user, parent, false), this)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.setData(items[position])
    }
}

class UserViewHolder(itemView: View, onClickListener: OnItemClickListener?) : BaseViewHolder<User>(itemView, onClickListener) {

    override fun setData(item: User) {
        itemView.text1.text = item.name
    }

}