package com.jit.cryptorate.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.database.CryptoDatabase
import com.jit.cryptorate.database.TimeDataStore
import com.jit.cryptorate.network.CryptoApiService
import com.jit.cryptorate.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CryptoRepository(
    private val serviceApi: CryptoApiService,
    private val database: CryptoDatabase,
    private val context: Context) {

    private val _crypto = MutableLiveData<CryptoData>()
    val crypto:LiveData<CryptoData> get() = _crypto

    suspend fun getCryptoRate(){
        withContext(Dispatchers.IO){

            if(NetworkUtils.isInternetAvailable(context)){
                val result = serviceApi.getCryptoData()
                if(result.data.isNotEmpty()){
                    if(database.cryptoDao().getCrypto().isNotEmpty()){
                        database.cryptoDao().deleteData()
                    }
                    database.cryptoDao().insertData(result.data)
                    TimeDataStore(context).saveTime(result.timestamp)
                    _crypto.postValue(result)
                }
            }else{

                if (database.cryptoDao().getCrypto().isNotEmpty()){
                    val data = database.cryptoDao().getCrypto()
                    var timeStamp:Long? = null
                    TimeDataStore(context).timeStampFlow.collect{
                        timeStamp = it
                    }
                    val cryptoData = CryptoData(data, timestamp = timeStamp!!)
                    _crypto.postValue(cryptoData)
                }
            }

        }
    }
}