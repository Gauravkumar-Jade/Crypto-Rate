package com.jit.cryptorate.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "CRYPTO_DATASTORE")

@Singleton
class TimeDataStore @Inject constructor(val context: Context) {

    companion object{
        val TIME_STAMP_KEY = longPreferencesKey("timeStamp")
    }

    suspend fun saveTime(time:Long){
        context.dataStore.edit {
            it[TIME_STAMP_KEY] = time
        }
    }

    val getTime:Flow<Long> = context.dataStore.data.map {
        it[TIME_STAMP_KEY]?:0
    }
}