package com.jit.cryptorate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jit.cryptorate.data.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class CryptoDatabase:RoomDatabase() {

    abstract fun cryptoDao():CryptoDao

}