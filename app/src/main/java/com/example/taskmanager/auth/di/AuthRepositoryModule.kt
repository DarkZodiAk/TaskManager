package com.example.taskmanager.auth.di

import com.example.taskmanager.auth.data.repository.AuthRepositoryImpl
import com.example.taskmanager.auth.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindAuthRepositoryImpl(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}