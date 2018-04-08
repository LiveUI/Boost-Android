package io.liveui.boost.ui.workspace

import android.os.Bundle
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.ext.replaceFragmentInActivity

class WorkspaceActivity: BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragmentInActivity(WorkspaceFragment(), android.R.id.content)

    }
}