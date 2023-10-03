package com.jit.cryptorate.di

import com.jit.cryptorate.BuildConfig
import com.jit.cryptorate.network.CryptoApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideHttpClient():OkHttpClient{

        val loggingInterceptor = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG){
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }else{
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient().newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.coincap.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }


    @Singleton
    @Provides
    fun provideCryptoAPI(retrofit: Retrofit):CryptoApiService{
        return retrofit.create(CryptoApiService::class.java)
    }
}