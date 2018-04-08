package io.liveui.boost.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import io.liveui.boost.common.UserSession
import io.liveui.boost.common.model.Workspace
import io.liveui.boost.util.permission.PermissionHelper
import javax.inject.Singleton

/**
 * Created by Vojtech Hrdina on 26/02/2018.
 */
@Module
class UtilModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application):
            SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideUserSession(): UserSession {
        return UserSession()
    }

    @Provides
    @Singleton
    fun providePermissionHelper(application: Application): PermissionHelper {
        return PermissionHelper(application)
    }

    @Provides
    @Singleton
    fun provideWorkspace(sharedPreferences: SharedPreferences): Workspace {
        return Workspace(sharedPreferences)
    }
}