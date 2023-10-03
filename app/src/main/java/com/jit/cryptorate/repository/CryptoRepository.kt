package com.jit.cryptorate.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.database.CryptoDatabase
import com.jit.cryptorate.database.TimeDataStore
import com.jit.cryptorate.network.CryptoApiService
import com.jit.cryptorate.utils.NetworkResult
import com.jit.cryptorate.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class CryptoRepository @Inject constructor(
    private val serviceApi: CryptoApiService,
    private val database: CryptoDatabase,
    private val timeDataStore: TimeDataStore,
    private val context: Context) {


    private val _crypto = MutableLiveData< NetworkResult<CryptoData>>()
    val crypto:LiveData<NetworkResult<CryptoData>> get() = _crypto

    suspend fun getCryptoRate(){
        withContext(Dispatchers.IO){
            _crypto.postValue(NetworkResult.Loading())
            if(NetworkUtils.isInternetAvailable(context)){
                try{
                    val response = serviceApi.getCryptoData()

                    if(response.isSuccessful && response.body() != null){

                        if(database.cryptoDao().getCrypto().isNotEmpty()){
                            database.cryptoDao().deleteData()
                        }
                        database.cryptoDao().insertData(response.body()!!.data)
                        timeDataStore.saveTime(response.body()!!.timestamp)

                        _crypto.postValue(NetworkResult.Success(response.body()!!))
                    }else{
                        val errorObj = JSONObject(response.errorBody()!!.string())
                        val message = errorObj.getString("message")
                        _crypto.postValue(NetworkResult.Error(code = response.code(), message = message))
                    }
                }catch (e:HttpException){
                    _crypto.postValue(NetworkResult.Error(code = e.code(), message = e.message))
                }catch (t:Throwable){
                    _crypto.postValue(NetworkResult.Exception(t))
                }
            }else{

                if (database.cryptoDao().getCrypto().isNotEmpty()){
                    val data = database.cryptoDao().getCrypto()
                    var timeStamp: Long?

                    timeDataStore.getTime.collect{
                        timeStamp = it

                        val cryptoData = CryptoData(data, timestamp = timeStamp!!)
                        _crypto.postValue(NetworkResult.NetworkError("No Network", cryptoData))
                    }
                }
            }
        }
    }
}