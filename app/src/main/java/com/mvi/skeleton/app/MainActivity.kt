package com.mvi.skeleton.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.mvi.skeleton.app.di.factories.FragmentInjectionFactory
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var fragmentFactory : FragmentFactory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val host: NavHostFragment = supportFragmentManager.
            findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = host.navController
        navController.navigate(R.id.loginFragment)
    }

    override fun androidInjector(): AndroidInjector<Any> =
        androidInjector

    override fun onSupportNavigateUp() =
        findNavController(R.id.nav_host).navigateUp()
}