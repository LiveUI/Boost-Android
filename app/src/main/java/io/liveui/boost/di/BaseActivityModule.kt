package io.liveui.boost.di

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import io.liveui.boost.di.scope.ActivityScope

@Module
abstract class BaseActivityModule {

    @Binds
    @ActivityScope
    abstract fun activity(appCompatActivity: AppCompatActivity): Activity

    @Binds
    @ActivityScope
    abstract fun activityContext(activity: Activity): Context

}