package com.example.taskmanager.auth.di

import com.example.taskmanager.auth.data.remote.AuthApi
import com.example.taskmanager.auth.domain.matcher.EmailMatcher
import com.example.taskmanager.auth.domain.matcher.PasswordMatcher
import com.example.taskmanager.auth.domain.repository.AuthRepository
import com.example.taskmanager.auth.domain.usecase.LoginUseCase
import com.example.taskmanager.auth.domain.usecase.LoginUseCases
import com.example.taskmanager.auth.domain.usecase.RegisterUseCase
import com.example.taskmanager.auth.domain.usecase.RegisterUseCases
import com.example.taskmanager.auth.domain.usecase.ValidateEmailUseCase
import com.example.taskmanager.auth.domain.usecase.ValidateNameUseCase
import com.example.taskmanager.auth.domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    @ViewModelScoped
    fun providesAuthApi(
        retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun providesLoginUseCases(
        authRepository: AuthRepository,
        emailMatcher: EmailMatcher,
        passwordMatcher: PasswordMatcher
    ): LoginUseCases {
        return LoginUseCases(
            loginUseCase = LoginUseCase(authRepository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase(passwordMatcher)
        )
    }

    @Provides
    @ViewModelScoped
    fun providesRegisterUseCases(
        authRepository: AuthRepository,
        emailMatcher: EmailMatcher,
        passwordMatcher: PasswordMatcher
    ): RegisterUseCases {
        return RegisterUseCases(
            registerUseCase = RegisterUseCase(authRepository),
            validatePasswordUseCase = ValidatePasswordUseCase(passwordMatcher),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validateNameUseCase = ValidateNameUseCase()
        )
    }
}