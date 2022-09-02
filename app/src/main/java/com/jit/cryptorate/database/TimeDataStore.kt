package com.jit.cryptorate.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val TIME_STAMP_NAME = "timeStampDetail"

private val Context.datsStore:DataStore<Preferences> by preferencesDataStore(
    name = TIME_STAMP_NAME)

private val TIME_STAMP_KEY = longPreferencesKey("timeStamp")

class TimeDataStore(val context: Context) {

    suspend fun saveTime(time:Long){
        context.datsStore.edit {
            it[TIME_STAMP_KEY] = time
        }
    }

    val timeStampFlow:Flow<Long> = context.datsStore.data.map {
        it[TIME_STAMP_KEY]?:0
    }

}