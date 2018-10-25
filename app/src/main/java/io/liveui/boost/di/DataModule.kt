package io.liveui.boost.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.BoostDatabase
import io.liveui.boost.db.WorkspaceDao
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): BoostDatabase {
        return Room.databaseBuilder(application.applicationContext, BoostDatabase::class.java, "boost-db").build()
    }

    @Provides
    fun provideWorkspaceDao(boostDatabase: BoostDatabase): WorkspaceDao {
        return boostDatabase.workspaceDao()
    }

    @Provides
    @Singleton
    fun provideUserSession(workspaceDao: WorkspaceDao): UserSession {
        return UserSession(workspaceDao)
    }
}