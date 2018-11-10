package io.liveui.boost.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.liveui.boost.api.BoostUrlProvider
import io.liveui.boost.api.TokenAuthenticator
import io.liveui.boost.api.service.BoostApiService
import io.liveui.boost.api.service.BoostAuthService
import io.liveui.boost.api.service.BoostCheckService
import io.liveui.boost.util.GsonUtil
import io.liveui.boost.util.UrlProvider
import io.liveui.boost.util.glide.GlideComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

const val GLIDE_CLIENT = "glideClient"

@Module(subcomponents = [(GlideComponent::class)])
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    fun provideGsonBuilder(): GsonBuilder = GsonBuilder()

    @Provides
    @Singleton
    fun provideGson(gsonUtil: GsonUtil): Gson {
        return gsonUtil.get()
    }

    @Provides
    @Singleton
    @Named(GLIDE_CLIENT)
    fun provideGlideClient(@Named(API_INTERCEPTORS) apiInterceptors: ArrayList<Interceptor>,
                           tokenAuthenticator: TokenAuthenticator): OkHttpClient {
        return OkHttpClient.Builder().apply {
            for (interceptor in apiInterceptors) {
                addInterceptor(interceptor)
                authenticator(tokenAuthenticator)
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideAuthService(cache: Cache,
                           gson: Gson,
                           urlProvider: UrlProvider,
                           @Named(AUTH_INTERCEPTORS) interceptors: ArrayList<Interceptor>): BoostAuthService {

        val client = OkHttpClient.Builder().apply {
            for (interceptor in interceptors) {
                addInterceptor(interceptor)
            }
            cache(cache)
        }.build()

        val retrofit = Retrofit.Builder().also {
            it.addConverterFactory(GsonConverterFactory.create(gson))
            it.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            it.baseUrl(urlProvider.getUrl())
            it.client(client)
        }.build()

        return retrofit.create(BoostAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(cache: Cache,
                          gson: Gson,
                          urlProvider: UrlProvider,
                          @Named(API_INTERCEPTORS) apiInterceptors: ArrayList<Interceptor>,
                          tokenAuthenticator: TokenAuthenticator): BoostApiService {

        val client = OkHttpClient.Builder().apply {
            for (interceptor in apiInterceptors) {
                addInterceptor(interceptor)
                authenticator(tokenAuthenticator)
                cache(cache)
            }
        }.build()

        val retrofit = Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            baseUrl(urlProvider.getUrl())
            client(client)
        }.build()

        return retrofit.create(BoostApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCheckService(cache: Cache,
                            gson: Gson,
                            urlProvider: UrlProvider,
                            @Named(LOGGING_INTERCEPTOR) interceptors: ArrayList<Interceptor>): BoostCheckService {
        val client = OkHttpClient.Builder().apply {
            for (interceptor in interceptors) {
                addInterceptor(interceptor)
            }
            cache(cache)
        }.build()

        val retrofit = Retrofit.Builder().also {
            it.addConverterFactory(GsonConverterFactory.create(gson))
            it.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            it.baseUrl(urlProvider.getUrl())
            it.client(client)
        }.build()

        return retrofit.create(BoostCheckService::class.java)
    }

    @Provides
    @Singleton
    fun provideUrlProvider(): UrlProvider {
        return BoostUrlProvider()
    }

}