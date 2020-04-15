package com.mvi.skeleton.app.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.mvi.skeleton.app.di.factories.FragmentInjectionFactory
import com.mvi.skeleton.auth.LoginFragment
import dagger.Binds
import dagger.Module

@Module
abstract class FragmentModule {

    @Binds
    abstract fun bindFragmentFactory(factory: FragmentInjectionFactory): FragmentFactory

    @Binds
    abstract fun bindsLoginFragment(loginFragment: LoginFragment): Fragment
}