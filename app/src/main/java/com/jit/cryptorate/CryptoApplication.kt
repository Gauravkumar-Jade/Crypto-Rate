package com.jit.cryptorate

import android.app.Application
import com.jit.cryptorate.di.CryptoComponent
import com.jit.cryptorate.di.DaggerCryptoComponent

class CryptoApplication:Application() {

    lateinit var cryptoComponent: CryptoComponent

    override fun onCreate() {
        super.onCreate()

        cryptoComponent = DaggerCryptoComponent.factory().create(this)
    }
}