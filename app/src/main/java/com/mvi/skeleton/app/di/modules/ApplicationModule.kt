package com.mvi.skeleton.app.di.modules

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.mvi.skeleton.app.MviSkeletonApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun app(): Application =
        MviSkeletonApplication()


    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

}