package com.jit.cryptorate.utils

sealed class NetworkResult<T>(val resultData: T? = null, val message: String? = null){

    class Success<T>(resultData: T?): NetworkResult<T>(resultData)

    class Error<T>(message: String?, resultData: T? = null): NetworkResult<T>(resultData,message)

    class NetworkError<T>(message: String?, resultData: T?): NetworkResult<T>(resultData, message)

    class Loading<T>(): NetworkResult<T>()
}
