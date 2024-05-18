package com.example.taskmanager.core.di

import android.content.Context
import com.example.taskmanager.BuildConfig
import com.example.taskmanager.core.data.connectivitymanager.ConnectivityHandlerImpl
import com.example.taskmanager.core.data.interceptors.AuthTokenInterceptor
import com.example.taskmanager.core.data.interceptors.UnauthorizedInterceptor
import com.example.taskmanager.core.domain.connectivitymanager.ConnectivityHandler
import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.local.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(
        preferences: UserPreferences,
        unauthorizedInterceptor: UnauthorizedInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthTokenInterceptor(preferences))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(unauthorizedInterceptor)
            .callTimeout(60L, TimeUnit.SECONDS)
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthEventBus(): AuthEventBus {
        return AuthEventBus()
    }

    @Provides
    @Singleton
    fun providesConnectivityHandler(@ApplicationContext context: Context): ConnectivityHandler {
        return ConnectivityHandlerImpl(context)
    }
}