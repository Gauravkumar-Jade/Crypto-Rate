package com.jit.cryptorate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jit.cryptorate.data.CryptoData
import com.jit.cryptorate.repository.CryptoRepository
import com.jit.cryptorate.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository):ViewModel() {

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