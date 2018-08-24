package io.liveui.boost.di

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import io.liveui.boost.di.scope.ActivityScope
import io.liveui.boost.ui.MainActivity

@Module(includes = [BaseActivityModule::class])
abstract class MainActivityModule {

    @Binds
    @ActivityScope
    abstract fun appCompatActivity(mainActivity: MainActivity): AppCompatActivity
}