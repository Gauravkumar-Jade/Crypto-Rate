package com.jit.cryptorate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jit.cryptorate.repository.CryptoRepository
import javax.inject.Inject

class CryptoViewModelFactory @Inject constructor(private val repository: CryptoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CryptoViewModel::class.java)){
            return CryptoViewModel(repository) as T
        }
        throw IllegalArgumentException("Not View Model")
    }
}