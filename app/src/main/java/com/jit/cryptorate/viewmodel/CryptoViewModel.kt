package com.jit.cryptorate.viewmodel

import androidx.lifecycle.*
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.repository.CryptoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.jit.cryptorate.data.Data

class CryptoViewModel(private val repository: CryptoRepository):ViewModel() {

    init {
      getCryptoValues()
    }

    fun getCryptoValues(){
        viewModelScope.launch {
            repository.getCryptoRate()
        }
    }

    val cryptoValue: LiveData<CryptoData> get() =  repository.crypto
}

class CryptoViewModelFactory(private val repository: CryptoRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CryptoViewModel::class.java)){
            return CryptoViewModel(repository) as T
        }
        throw IllegalArgumentException("Not View Model")
    }
}