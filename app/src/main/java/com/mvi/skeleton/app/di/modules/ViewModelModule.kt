package com.mvi.skeleton.app.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvi.skeleton.app.di.factories.DaggerInjectingViewModelFactory
import com.mvi.skeleton.auth.LoginViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerInjectingViewModelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun authViewModel(authViewModel: LoginViewModel): ViewModel
}