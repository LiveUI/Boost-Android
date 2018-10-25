package io.liveui.boost.di

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
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
    fun providePermissionHelper(application: Application): PermissionHelper {
        return PermissionHelper(application)
    }

    @Provides
    @Singleton
    fun provideAndroidDownloadManager(application: Application): DownloadManager {
        return application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

}