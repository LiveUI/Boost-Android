package io.liveui.boost.di

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.util.StackManager

@Module
class ActivityModule {

    @Provides
    @ActivityScope
    fun provideStackManager(appCompatActivity: AppCompatActivity): StackManager {
        return StackManager(appCompatActivity)
    }
    
}