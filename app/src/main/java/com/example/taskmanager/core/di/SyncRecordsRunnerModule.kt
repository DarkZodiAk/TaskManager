package com.example.taskmanager.core.di

import com.example.taskmanager.core.data.worker.SyncRecordsRunnerImpl
import com.example.taskmanager.core.domain.worker.SyncRecordsRunner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncRecordsRunnerModule {
    @Binds
    @Singleton
    abstract fun bindSyncRecordsRunnerImpl(
        syncRecordsRunnerImpl: SyncRecordsRunnerImpl
    ): SyncRecordsRunner
}