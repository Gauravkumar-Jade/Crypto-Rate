package com.jit.cryptorate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jit.cryptorate.data.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class CryptoDatabase:RoomDatabase() {

    abstract fun cryptoDao():CryptoDao

    companion object{
        private var INSTANT:CryptoDatabase? = null

        fun getDatabase(context: Context):CryptoDatabase{
            if(INSTANT == null){
                synchronized(this){
                    INSTANT = Room.databaseBuilder(
                        context,
                        CryptoDatabase::class.java,
                        "crypto_db")
                        .build()
                }
            }

            return INSTANT!!
        }
    }
}