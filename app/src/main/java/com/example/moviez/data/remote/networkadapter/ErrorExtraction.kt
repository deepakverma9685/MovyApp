package com.example.moviez.data.remote.networkadapter

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.IOException

internal const val UNKNOWN_ERROR_RESPONSE_CODE = 520

internal fun <S : Any, E : Any> HttpException.extractFromHttpException(errorConverter: Converter<ResponseBody, E>): NetworkResponse<S, E> {
    val error = response()?.errorBody()
    val responseCode = response()?.code() ?: UNKNOWN_ERROR_RESPONSE_CODE
    val headers = response()?.headers()
    val errorBody = when {
        error == null -> null // No error content available
        error.contentLength() == 0L -> null // Error content is empty
        else -> try {
            // There is error content present, so we should try to extract it
            errorConverter.convert(error)
        } catch (e: Exception) {
            // If unable to extract content, return with a null body and don't parse further
            return NetworkResponse.ServerError(null, responseCode, headers, getErrorMessage(responseCode))
        }
    }
    return NetworkResponse.ServerError(errorBody, responseCode, headers, getErrorMessage(responseCode))
}

internal fun <S : Any, E : Any> Throwable.extractNetworkResponse(errorConverter: Converter<ResponseBody, E>): NetworkResponse<S, E> {
    return when (this) {
        is IOException -> NetworkResponse.NetworkError(this)
        is HttpException -> extractFromHttpException<S, E>(errorConverter)
        else -> throw this
    }
}

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1),
    IO(-2)
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