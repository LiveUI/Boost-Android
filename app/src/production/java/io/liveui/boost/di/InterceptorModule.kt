package io.liveui.boost.di

import dagger.Module
import dagger.Provides
import io.liveui.boost.BuildConfig
import io.liveui.boost.api.AddHeaderAuthInterceptor
import io.liveui.boost.api.SaveAuthInterceptor
import io.liveui.boost.api.UrlInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

const val LOGGING_INTERCEPTOR = "loggingInterceptor"
const val URL_INTERCEPTOR = "urlInterceptor"
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
    @Named(URL_INTERCEPTOR)
    fun provideUrlBaseInterceptors(urlInterceptor: UrlInterceptor): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            add(urlInterceptor)
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
    fun provideApiInterceptors(urlInterceptor: UrlInterceptor,
                               addHeaderAuthInterceptor: AddHeaderAuthInterceptor): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            add(addHeaderAuthInterceptor)
            add(urlInterceptor)
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
    fun provideAuthInterceptors(urlInterceptor: UrlInterceptor,
                                saveAuthInterceptor: SaveAuthInterceptor): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            add(saveAuthInterceptor)
            add(urlInterceptor)
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
        }
    }
}