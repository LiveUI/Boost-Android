package cz.mangoweb.appstore.di

import android.content.SharedPreferences
import cz.mangoweb.appstore.BuildConfig
import cz.mangoweb.appstore.SaveAuthInterceptor
import cz.mangoweb.appstore.api.AddHeaderAuthInterceptor
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
    @Named("apiInterceptors")
    fun provideApiInterceptors(sharedPreferences: SharedPreferences): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
            add(AddHeaderAuthInterceptor(sharedPreferences.getString("jwtToken", null)))
        }
    }

    @Provides
    @Singleton
    @Named("authInterceptors")
    fun provideAuthInterceptors(sharedPreferences: SharedPreferences): ArrayList<Interceptor> {
        return ArrayList<Interceptor>().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                add(httpLoggingInterceptor)
            }
            add(SaveAuthInterceptor(sharedPreferences))
        }
    }
}