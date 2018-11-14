package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.BuildConfig
import io.liveui.boost.api.AddHeaderAuthInterceptor
import io.liveui.boost.api.SaveAuthInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

const val LOGGING_INTERCEPTOR = "loggingInterceptor"
const val API_INTERCEPTORS = "apiInterceptors"
const val AUTH_INTERCEPTORS = "authInterceptors"

@Module
class InterceptorModule {

    @Provides
    @Singleton
    @Named(LOGGING_INTERCEPTOR)
    fun provideLoggingInterceptors(): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
        }
    }

    @Provides
    @Singleton
    @Named(API_INTERCEPTORS)
    fun provideApiInterceptors(addHeaderAuthInterceptor: AddHeaderAuthInterceptor): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            add(addHeaderAuthInterceptor)
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
        }
    }

    @Provides
    @Singleton
    @Named(AUTH_INTERCEPTORS)
    fun provideAuthInterceptors(saveAuthInterceptor: SaveAuthInterceptor): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            add(saveAuthInterceptor)
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
        }
    }
}