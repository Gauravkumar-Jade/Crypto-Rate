package com.jit.cryptorate.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


data class CryptoData(
    var data: List<Data>,
    var timestamp: Long
)

@Entity(tableName = "cryptoValue")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var ids: Int = 0,
    var name: String?,
    var priceUsd: String?,
    var symbol: String?
)