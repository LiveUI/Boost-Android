package io.liveui.boost.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.liveui.boost.ui.intro.ChooseServerFragment
import io.liveui.boost.ui.login.LoginFragment
import io.liveui.boost.ui.register.RegisterFragment

@Module
abstract class LoginModule {

    @ContributesAndroidInjector
    abstract fun provideChooseServerFragment(): ChooseServerFragment

    @ContributesAndroidInjector
    abstract fun provideLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun provideRegisterFragment(): RegisterFragment

}