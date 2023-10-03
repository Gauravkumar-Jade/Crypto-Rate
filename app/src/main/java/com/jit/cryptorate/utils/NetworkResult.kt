package com.jit.cryptorate.utils

sealed class NetworkResult<T>(){

    class Success<T>(val resultData: T?): NetworkResult<T>()

    class Error<T>(val code:Int, val message:String?): NetworkResult<T>()

    class NetworkError<T>(val message: String?, val resultData: T?): NetworkResult<T>()

    class Exception<T>(val e:Throwable): NetworkResult<T>()

    class Loading<T>(): NetworkResult<T>()
}
