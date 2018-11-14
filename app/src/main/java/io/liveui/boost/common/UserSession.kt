package io.liveui.boost.common

import io.liveui.boost.api.model.RefreshTokenResponse
import io.liveui.boost.db.Workspace
import io.liveui.boost.db.WorkspaceDao
import io.liveui.boost.util.UrlProvider
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor(val workspaceDao: WorkspaceDao,
                                      val urlProvider: UrlProvider) {

    val activeWorkspace = workspaceDao.getActiveWorkspace()
    private val activeJwtToken = workspaceDao.getActiveJwtToken()
    var workspace: Workspace? = null
    var jwtToken: String? = null
    var permanentToken: String? = null

    init {
        activeWorkspace.observeForever {
            workspace = it
            permanentToken = it?.permToken
            it?.url?.let { url ->
                urlProvider.setUrl(url)
            }

            Timber.d("Set Active Workspace: ${it?.toString()}")
        }
        activeJwtToken.observeForever {
            jwtToken = it

            Timber.d("Set Active JWT Token: $it")
        }
    }

    fun setTokenInfo(tokenInfo: RefreshTokenResponse) {

    }

}