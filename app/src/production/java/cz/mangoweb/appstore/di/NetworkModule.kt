package cz.mangoweb.appstore.di

import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cz.mangoweb.appstore.BuildConfig
import cz.mangoweb.appstore.api.TokenAuthenitcator
import cz.mangoweb.appstore.api.service.BoostApiService
import cz.mangoweb.appstore.api.service.BoostAuthService
import cz.mangoweb.appstore.api.service.BoostDownloadService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    @Named("apiClient")
    fun provideApiOkHttpClient(cache: Cache, authService: BoostAuthService, sharedPreferences: SharedPreferences): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            client.addInterceptor(httpLoggingInterceptor)
        }
        client.cache(cache)
        client.authenticator(TokenAuthenitcator(authService, sharedPreferences))
        return client.build()
    }

    @Provides
    @Singleton
    @Named("authClient")
    fun provideAuthOkHttpClient(cache: Cache): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(httpLoggingInterceptor)
        }
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    @Named("apiRetrofit")
    fun provideApiRetrofit(gson: Gson, @Named("apiClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    @Named("authRetrofit")
    fun provideAuthRetrofit(gson: Gson, @Named("authClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(@Named("authRetrofit") retrofit: Retrofit): BoostAuthService {
        return retrofit.create(BoostAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(@Named("apiRetrofit") retrofit: Retrofit): BoostApiService {
        return retrofit.create(BoostApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDownloadService(@Named("apiRetrofit") retrofit: Retrofit): BoostDownloadService {
        return retrofit.create(BoostDownloadService::class.java)
    }

}