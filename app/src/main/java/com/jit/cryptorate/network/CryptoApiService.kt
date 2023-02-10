package com.jit.cryptorate.network

import com.jit.cryptorate.data.CryptoData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface CryptoApiService{

    @GET("v2/assets")
    suspend fun getCryptoData():Response<CryptoData>
}
