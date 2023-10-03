package com.jit.cryptorate.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.jit.cryptorate.data.Data
import com.jit.cryptorate.utils.NetworkResult

class CryptoViewModel(private val repository: CryptoRepository):ViewModel() {

    init {
      getCryptoValues()
    }

    fun getCryptoValues(){
        viewModelScope.launch {
            repository.getCryptoRate()
        }
    }

    val cryptoValue: LiveData<NetworkResult<CryptoData>> get() =  repository.crypto


}