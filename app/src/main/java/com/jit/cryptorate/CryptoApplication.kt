package com.jit.cryptorate

import android.app.Application
import com.jit.cryptorate.database.CryptoDatabase
import com.jit.cryptorate.network.CryptoApi
import com.jit.cryptorate.repository.CryptoRepository

class CryptoApplication:Application() {

    val repository:CryptoRepository by lazy {
        CryptoRepository(
            CryptoApi.retrofitService,
            CryptoDatabase.getDatabase(applicationContext),
            applicationContext)
    }
}