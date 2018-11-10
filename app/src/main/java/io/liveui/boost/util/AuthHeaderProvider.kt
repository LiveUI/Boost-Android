package io.liveui.boost.util

import io.liveui.boost.common.UserSession
import io.liveui.boost.db.WorkspaceDao
import okhttp3.Request
import javax.inject.Inject

const val HEADER_AUTHORIZATION = "Authorization"

class AuthHeaderProvider @Inject constructor(val userSession: UserSession,
                                             val workspaceDao: WorkspaceDao) : HeaderProvider {
    //Header mapper?
    override fun getHeader(): String? {
        return userSession.jwtToken
    }

    override fun saveHeader(token: String): Int {
        return workspaceDao.updateActiveJwtToken(token)
    }

    override fun applyHeader(builder: Request.Builder?): Request.Builder? {
        return builder?.apply {
            getHeader()?.apply {
                removeHeader(HEADER_AUTHORIZATION)
                addHeader(HEADER_AUTHORIZATION, this)
            }
        }
    }

}