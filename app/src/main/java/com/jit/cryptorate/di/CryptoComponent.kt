package com.jit.cryptorate.di

import android.content.Context
import com.jit.cryptorate.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface CryptoComponent {

    fun inject(activity: MainActivity)


    @Component.Factory
    interface factory{
        fun create(@BindsInstance context: Context): CryptoComponent
    }
}