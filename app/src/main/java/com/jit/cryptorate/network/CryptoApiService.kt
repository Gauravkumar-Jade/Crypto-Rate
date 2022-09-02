package com.jit.cryptorate.network

import com.jit.cryptorate.data.CryptoData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.coincap.io/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CryptoApiService{

    @GET("v2/assets")
    suspend fun getCryptoData():CryptoData
}

object CryptoApi{

    val retrofitService:CryptoApiService by lazy {
        retrofit.create(CryptoApiService::class.java)
    }
}