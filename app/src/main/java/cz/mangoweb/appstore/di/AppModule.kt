package cz.mangoweb.appstore.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application):
            SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}