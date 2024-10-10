package com.example.taskmanager.auth.di

import com.example.taskmanager.auth.data.matcher.PasswordMatcherImpl
import com.example.taskmanager.auth.domain.matcher.PasswordMatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class PasswordMatcherModule {
    @Binds
    @ViewModelScoped
    abstract fun bindPasswordMatcherImpl(
        passwordMatcherImpl: PasswordMatcherImpl
    ): PasswordMatcher
}