package io.liveui.boost.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.liveui.boost.di.ApiModule
import io.liveui.boost.di.AuthModule
import io.liveui.boost.di.CheckModule
import io.liveui.boost.ui.intro.ChooseServerFragment
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.ui.register.RegisterFragment

@Module
abstract class LoginModule {

    @ContributesAndroidInjector(modules = [ApiModule::class, CheckModule::class])
    abstract fun provideChooseServerFragment(): ChooseServerFragment

    @ContributesAndroidInjector(modules = [ApiModule::class, AuthModule::class])
    abstract fun provideLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [ApiModule::class])
    abstract fun provideRegisterFragment(): RegisterFragment

}