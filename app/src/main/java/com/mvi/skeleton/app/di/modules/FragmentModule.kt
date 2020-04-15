package com.mvi.skeleton.app.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.mvi.skeleton.app.di.factories.FragmentInjectionFactory
import com.mvi.skeleton.app.di.annotation.FragmentKey
import com.mvi.skeleton.auth.LoginFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

    @Binds
    abstract fun bindFragmentFactory(factory: FragmentInjectionFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    abstract fun bindsLoginFragment(loginFragment: LoginFragment): Fragment
}