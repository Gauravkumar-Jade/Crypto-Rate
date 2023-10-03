package com.jit.cryptorate.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jit.cryptorate.database.CryptoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto_db")
            .build()
    }


    @Provides
    fun provideContext(@ApplicationContext context: Context):Context{
        return context
    }
}