package com.os.onlinefood.util

sealed class Result<out T> {
    data class Success<out T>(val data: Any?) : Result<T>()
    data class SuccessMsg(val msg:String) : Result<Nothing>()
    data class ShowResponse(val msg:Int) :Result<Nothing>()
    data class Loading(val status: Boolean) : Result<Nothing>()
    sealed class Error(val exception:Exception) : Result<Nothing>() {
        class RecoverableError(exception: Exception) : Error(exception)
        class NonRecoverableError(exception: Exception) : Error(exception)
    }
}




