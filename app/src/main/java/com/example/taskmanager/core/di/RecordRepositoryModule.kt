package com.example.taskmanager.core.di

import com.example.taskmanager.core.data.repository.RecordRepositoryImpl
import com.example.taskmanager.core.domain.repository.RecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RecordRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRecordRepositoryImpl(
        recordRepositoryImpl: RecordRepositoryImpl
    ): RecordRepository
}