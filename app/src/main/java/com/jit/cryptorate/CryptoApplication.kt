package com.jit.cryptorate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptoApplication:Application() {


    override fun onCreate() {
        super.onCreate()

    }
}