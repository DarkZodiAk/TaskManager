package com.example.taskmanager.core.domain.usecase

import com.example.taskmanager.auth.domain.usecase.AuthenticateUseCase
import com.example.taskmanager.auth.domain.usecase.LogoutUseCase
import com.example.taskmanager.records.domain.usecase.PeriodicLoadRecordsUseCase
import com.example.taskmanager.records.domain.usecase.PeriodicSyncRecordsUseCase
import javax.inject.Inject

data class MainUseCases @Inject constructor(
    val authenticateUseCase: AuthenticateUseCase,
    val logoutUseCase: LogoutUseCase,
    val clearDatabaseUseCase: ClearDatabaseUseCase,
    val periodicLoadRecordsUseCase: PeriodicLoadRecordsUseCase,
    val periodicSyncRecordsUseCase: PeriodicSyncRecordsUseCase
)