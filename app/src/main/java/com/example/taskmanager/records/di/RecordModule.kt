package com.example.taskmanager.records.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.taskmanager.records.data.local.RecordDatabase
import com.example.taskmanager.records.data.local.dao.ModifiedRecordDAO
import com.example.taskmanager.records.data.local.dao.RecordDAO
import com.example.taskmanager.records.data.remote.RecordApi
import com.example.taskmanager.records.data.repository.RecordRepositoryImpl
import com.example.taskmanager.records.data.worker.LoadRecordsRunnerImpl
import com.example.taskmanager.records.data.worker.SyncRecordsRunnerImpl
import com.example.taskmanager.records.domain.repository.RecordRepository
import com.example.taskmanager.records.domain.worker.LoadRecordsRunner
import com.example.taskmanager.records.domain.worker.SyncRecordsRunner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecordModule {
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
    fun providesRecordApi(retrofit: Retrofit): RecordApi {
        return retrofit.create(RecordApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRecordRepository(
        recordDAO: RecordDAO,
        modifiedRecordDAO: ModifiedRecordDAO,
        api: RecordApi
    ): RecordRepository {
        return RecordRepositoryImpl(recordDAO, modifiedRecordDAO, api)
    }

    @Provides
    @Singleton
    fun providesWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun providesSyncRecordsRunner(workManager: WorkManager): SyncRecordsRunner {
        return SyncRecordsRunnerImpl(workManager)
    }

    @Provides
    @Singleton
    fun providesLoadRecordsRunner(workManager: WorkManager): LoadRecordsRunner {
        return LoadRecordsRunnerImpl(workManager)
    }
}