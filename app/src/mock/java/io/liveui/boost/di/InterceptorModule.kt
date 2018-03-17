package io.liveui.boost.di

import android.app.Application
import android.content.SharedPreferences
import io.liveui.boost.BuildConfig
import io.liveui.boost.SaveAuthInterceptor
import io.liveui.boost.api.AddHeaderAuthInterceptor
import io.liveui.boost.api.MockInterceptor
import io.liveui.boost.api.MockResponseResolver
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
class InterceptorModule {

    @Provides
    @Singleton
    fun provideMockResponseResolver(appStoreApplication: Application): MockResponseResolver {
        return MockResponseResolver(appStoreApplication)
    }

    @Provides
    @Singleton
    @Named("apiInterceptors")
    fun provideApiInterceptors(sharedPreferences: SharedPreferences, mockResponseResolver: MockResponseResolver): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
            add(AddHeaderAuthInterceptor(sharedPreferences.getString("jwtToken", null)))
            add(MockInterceptor(mockResponseResolver))
        }
    }

    @Provides
    @Singleton
    @Named("authInterceptors")
    fun provideAuthInterceptors(sharedPreferences: SharedPreferences, mockResponseResolver: MockResponseResolver): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
            add(SaveAuthInterceptor(sharedPreferences))
            add(MockInterceptor(mockResponseResolver))
        }
    }
}