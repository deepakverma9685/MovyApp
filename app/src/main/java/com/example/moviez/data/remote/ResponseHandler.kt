package com.example.moviez.data.remote

import com.example.moviez.utils.LogUtils
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    IO(-2)
}

open class ResponseHandler {
    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleLoading(): Resource<T> {
        return Resource.loading(null)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        LogUtils.e("ResponseHandler", e.toString())
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            is IOException -> Resource.error(getErrorMessage(ErrorCodes.IO.code), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    fun <T> handleError(msg: String): Resource<T> {
        return Resource.error(msg, null)
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout Error"
            ErrorCodes.IO.code -> "An IO error has occurred, most likely a network issue. Please check your internet connection and try again"
            401 -> "Unauthorised"
            404 -> "Not found"
            500 -> "Internal server error"
            504 -> "Gateway Time-out"
            else -> "Something went wrong"
        }
    }
}

