package io.liveui.boost.di

import android.content.SharedPreferences
import io.liveui.boost.BuildConfig
import io.liveui.boost.api.AddHeaderAuthInterceptor
import dagger.Module
import dagger.Provides
import io.liveui.boost.api.BaseUrlInterceptor
import io.liveui.boost.api.SaveAuthInterceptor
import io.liveui.boost.common.UserSession
import io.liveui.boost.db.BoostDatabase
import io.liveui.boost.db.Workspace
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class InterceptorModule {

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    fun provideBaseInterceptors(userSession: UserSession): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            val workspace = userSession.workspace
            add(BaseUrlInterceptor(workspace))
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
        }
    }

    @Provides
    @Singleton
    @Named("apiInterceptors")
    fun provideApiInterceptors(@Named("loggingInterceptor") loggingInterceptor: ArrayList<Interceptor>, sharedPreferences: SharedPreferences): ArrayList<Interceptor> {
        return loggingInterceptor.apply {
            add(AddHeaderAuthInterceptor(sharedPreferences))
        }
    }

    @Provides
    @Singleton
    @Named("authInterceptors")
    fun provideAuthInterceptors(@Named("loggingInterceptor") loggingInterceptor: ArrayList<Interceptor>, sharedPreferences: SharedPreferences): ArrayList<Interceptor> {
        return loggingInterceptor.apply {
            add(SaveAuthInterceptor(sharedPreferences))
        }
    }
}