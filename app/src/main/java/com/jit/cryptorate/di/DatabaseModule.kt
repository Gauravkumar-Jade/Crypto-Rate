package com.jit.cryptorate.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jit.cryptorate.database.CryptoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto_db")
            .build()
    }
}