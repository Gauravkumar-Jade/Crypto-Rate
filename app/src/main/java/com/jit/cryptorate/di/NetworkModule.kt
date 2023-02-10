package com.jit.cryptorate.di

import com.jit.cryptorate.network.CryptoApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!


    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.coincap.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Singleton
    @Provides
    fun provideCryptoAPI(retrofit: Retrofit):CryptoApiService{
        return retrofit.create(CryptoApiService::class.java)
    }
}