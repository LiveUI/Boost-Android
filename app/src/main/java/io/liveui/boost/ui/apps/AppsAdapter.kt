package io.liveui.boost.ui.apps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import io.liveui.boost.R
import io.liveui.boost.api.model.App
import io.liveui.boost.download.DownloadStatus
import io.liveui.boost.ui.view.adapter.BaseObservableAdapter
import io.liveui.boost.ui.view.adapter.BaseViewHolder
import io.liveui.boost.ui.view.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.view_holder_app.view.*
import javax.inject.Inject
import javax.inject.Provider

class AppsAdapter @Inject constructor(val baseAppViewModelProvider: Provider<AppViewModel>) : BaseObservableAdapter<App, AppsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppsViewHolder {
        return AppsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.view_holder_app, parent, false),
                this,
                baseAppViewModelProvider.get())
    }

    override fun onBindViewHolder(holder: AppsViewHolder, position: Int) {
        holder.setData(items[position])
    }
}

class AppsViewHolder(itemView: View,
                     onClickListener: OnItemClickListener?,
                     val baseAppViewModel: AppViewModel) : BaseViewHolder<App>(itemView, onClickListener) {

    init {
        itemView.setOnClickListener(this)
        itemView.btn_download.setOnClickListener(this)
        itemView.btn_open.setOnClickListener(this)
        itemView.btn_more.setOnClickListener(this)
        itemView.btn_settings.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_download -> {
                baseAppViewModel.downloadApp()
            }
            R.id.btn_settings -> {
                baseAppViewModel.openSettings()
            }
            R.id.btn_open -> {
                baseAppViewModel.openApp()
            }
            R.id.btn_more -> {
                showMenuMore(v)
            }
            else -> {
                baseAppViewModel.openAppDetail()
            }
        }
    }

    override fun setData(item: App) {
        baseAppViewModel.app = item
        baseAppViewModel.downloadStatus.observeForever {
            when {
                it == DownloadStatus.COMPLETED -> {
                    itemView.group_app_downloaded.visibility = View.VISIBLE
                    itemView.btn_download.visibility = View.GONE
                    itemView.download_progress_bar.visibility = View.GONE
                }
                arrayListOf(DownloadStatus.IN_PROGRESS, DownloadStatus.TOKEN_VERIFICATION_STARTED, DownloadStatus.TOKEN_VERIFICATION_COMPLETE).contains(it) -> {
                    itemView.download_progress_bar.visibility = View.VISIBLE
                    itemView.group_app_downloaded.visibility = View.GONE
                    itemView.btn_download.visibility = View.GONE
                }
                else -> {
                    itemView.download_progress_bar.visibility = View.GONE
                    itemView.group_app_downloaded.visibility = View.GONE
                    itemView.btn_download.visibility = View.VISIBLE
                }
            }
        }
        itemView.app_name.text = item.name
        itemView.app_version.text = item.version
        itemView.app_identifier.text = item.identifier

        baseAppViewModel.loadAppIcon(itemView.app_logo, item.id)
    }

    fun showMenuMore(view: View) {
        val menu = PopupMenu(view.context, view)
        menu.inflate(R.menu.menu_more)
        menu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_download -> {
                    baseAppViewModel.downloadApp()
                }
            }
            true
        }
        menu.show()
    }


}