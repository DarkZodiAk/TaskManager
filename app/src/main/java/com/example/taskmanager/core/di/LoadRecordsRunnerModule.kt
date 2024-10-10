package com.example.taskmanager.core.di

import com.example.taskmanager.core.data.worker.LoadRecordsRunnerImpl
import com.example.taskmanager.core.domain.worker.LoadRecordsRunner
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoadRecordsRunnerModule {
    @Binds
    @Singleton
    abstract fun bindLoadRecordsRunnerImpl(
        loadRecordsRunnerImpl: LoadRecordsRunnerImpl
    ): LoadRecordsRunner
}