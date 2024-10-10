package com.example.taskmanager.auth.di

import com.example.taskmanager.auth.data.matcher.EmailMatcherImpl
import com.example.taskmanager.auth.domain.matcher.EmailMatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class EmailMatcherModule {
    @Binds
    @ViewModelScoped
    abstract fun bindEmailMatcherImpl(
        emailMatcherImpl: EmailMatcherImpl
    ): EmailMatcher
}