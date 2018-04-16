package io.liveui.boost.ui.workspace.add

import android.os.Bundle
import io.liveui.boost.ui.BoostActivity
import io.liveui.boost.util.ext.replaceFragmentInActivity

class WorkspaceAddActivity: BoostActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragmentInActivity(WorkspaceAddFragment(), android.R.id.content)

    }
}