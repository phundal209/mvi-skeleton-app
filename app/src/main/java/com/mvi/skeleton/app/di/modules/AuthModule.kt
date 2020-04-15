package com.mvi.skeleton.app.di.modules

import com.mvi.skeleton.auth.api.AuthRepository
import com.mvi.skeleton.auth.data.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindsAuthApi(authApiImpl: AuthRepositoryImpl): AuthRepository
}