package com.example.taskmanager.core.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.taskmanager.BuildConfig
import com.example.taskmanager.core.data.connectivitymanager.ConnectivityHandlerImpl
import com.example.taskmanager.core.data.interceptors.AuthTokenInterceptor
import com.example.taskmanager.core.data.interceptors.UnauthorizedInterceptor
import com.example.taskmanager.core.data.local.RecordDatabase
import com.example.taskmanager.core.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.core.data.local.dao.RecordDAO
import com.example.taskmanager.core.data.repository.RecordRepositoryImpl
import com.example.taskmanager.core.domain.connectivitymanager.ConnectivityHandler
import com.example.taskmanager.core.domain.eventBus.AuthEventBus
import com.example.taskmanager.core.domain.local.UserPreferences
import com.example.taskmanager.core.domain.repository.RecordRepository
import com.example.taskmanager.records.data.remote.RecordApi
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
    fun providesRecordDatabase(@ApplicationContext context: Context): RecordDatabase {
        return Room.databaseBuilder(
            context,
            RecordDatabase::class.java,
            "record_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesRecordDAO(recordDatabase: RecordDatabase): RecordDAO {
        return recordDatabase.recordDao()
    }

    @Provides
    @Singleton
    fun providesModifiedRecordDAO(recordDatabase: RecordDatabase): ModifiedRecordDAO {
        return recordDatabase.modifiedRecordDAO()
    }

    @Provides
    @Singleton
    fun providesWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}