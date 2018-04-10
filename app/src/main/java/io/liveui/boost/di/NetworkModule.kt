package io.liveui.boost.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.liveui.boost.BuildConfig
import io.liveui.boost.api.TokenAuthenticator
import io.liveui.boost.api.service.BoostApiService
import io.liveui.boost.api.service.BoostAuthService
import io.liveui.boost.api.service.BoostDownloadService
import dagger.Module
import dagger.Provides
import io.liveui.boost.api.service.BoostCheckService
import io.liveui.boost.common.UserSession
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
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
    fun provideApiOkHttpClient(cache: Cache, authService: BoostAuthService, userSession: UserSession, @Named("apiInterceptors") interceptors: ArrayList<Interceptor>): OkHttpClient {
        val client = OkHttpClient.Builder()
        for (interceptor in interceptors) {
            client.addInterceptor(interceptor)
        }
        client.authenticator(TokenAuthenticator(authService, userSession.workspace))
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    @Named("authClient")
    fun provideAuthOkHttpClient(cache: Cache, @Named("authInterceptors") interceptors: ArrayList<Interceptor>): OkHttpClient {
        val client = OkHttpClient.Builder()
        for (interceptor in interceptors) {
            client.addInterceptor(interceptor)
        }
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    @Named("baseClient")
    fun provideBaseOkHttpClient(cache: Cache, @Named("loggingInterceptor") interceptors: ArrayList<Interceptor>): OkHttpClient {
        val client = OkHttpClient.Builder()
        for (interceptor in interceptors) {
            client.addInterceptor(interceptor)
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
                .baseUrl(BuildConfig.URL[0])
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
                .baseUrl(BuildConfig.URL[0])
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    @Named("baseRetrofit")
    fun provideBaseRetrofit(gson: Gson, @Named("baseClient") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.URL[0])
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

    @Provides
    @Singleton
    fun provideCheckService(@Named("baseRetrofit") retrofit: Retrofit): BoostCheckService {
        return retrofit.create(BoostCheckService::class.java)
    }

}