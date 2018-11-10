package io.liveui.boost.ui.workspace.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import io.liveui.boost.R
import io.liveui.boost.db.Workspace
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import io.liveui.boost.util.glide.GlideProvider
import io.liveui.boost.util.navigation.FragmentNavigationItem
import io.liveui.boost.util.navigation.MainNavigator
import kotlinx.android.synthetic.main.view_holder_workspace.view.*
import javax.inject.Inject
import javax.inject.Provider

class WorkspaceListAdapter @Inject constructor(val itemViewModelProvider: Provider<WorkspaceListItemViewModel>,
                                               val glideProvider: GlideProvider) : BaseObservableAdapter<Workspace, WorkspaceViewHolder>() {

    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var mainNavigator: MainNavigator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        return WorkspaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_workspace, parent, false),
                this,
                lifecycleOwner,
                mainNavigator,
                glideProvider,
                itemViewModelProvider.get())
    }

    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun addItems(newItems: MutableList<Workspace>?) {
        newItems?.sortedWith(compareBy(Workspace::status, Workspace::name))
        super.addItems(newItems)
    }

}

class WorkspaceViewHolder(itemView: View,
                          onClickListener: OnItemClickListener?,
                          val lifecycleOwner: LifecycleOwner,
                          val mainNavigator: MainNavigator,
                          val glideProvider: GlideProvider,
                          val workspaceListItemViewModel: WorkspaceListItemViewModel)
    : BaseViewHolder<Workspace>(itemView, onClickListener) {

    override fun setData(item: Workspace) {
        workspaceListItemViewModel.workspace.postValue(item)
        itemView.indicator.isEnabled = item.active == 1
        workspaceListItemViewModel.serverName.observeForever {
            itemView.name.text = it
        }
        workspaceListItemViewModel.serverUrl.observeForever {
            itemView.url.text = it
        }
        workspaceListItemViewModel.serverStatusIcon.observeForever {
            itemView.server_status.setImageResource(it)
        }
        workspaceListItemViewModel.iconUrl.observe(lifecycleOwner, Observer {
            it?.let {
                glideProvider.loadAppIconFromUrl(imageView = itemView.icon, url = it)
            }
        })
    }

    override fun onClick(v: View) {
        workspaceListItemViewModel.onItemClick {
            mainNavigator.replaceFragment(FragmentNavigationItem(clazz = LoginFragment::class.java, addToBackStack = true))
        }
    }

}