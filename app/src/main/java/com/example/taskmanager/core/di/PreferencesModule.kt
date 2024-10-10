package com.example.taskmanager.core.di

import com.example.taskmanager.core.data.local.UserPreferencesImpl
import com.example.taskmanager.core.domain.local.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {
    @Binds
    @Singleton
    abstract fun bindsUserPreferences(
        impl: UserPreferencesImpl
    ): UserPreferences
}