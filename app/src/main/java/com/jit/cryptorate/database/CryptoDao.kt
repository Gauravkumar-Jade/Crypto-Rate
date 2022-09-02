package com.jit.cryptorate.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.data.Data

@Dao
interface CryptoDao {

    @Insert
    suspend fun insertData(cryptoData:List<Data>)

    @Query("DELETE FROM cryptoValue")
    suspend fun deleteData()

    @Query("SELECT * FROM cryptoValue")
    suspend fun getCrypto() : List<Data>
}