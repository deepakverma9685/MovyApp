package com.example.moviez.data.remote.networkadapter

import okhttp3.Headers
import retrofit2.Response
import java.io.IOException

sealed class NetworkResponse<out T, out U : Any> {
    companion object {
        fun <T : Any> success( body: T,  headers: Headers? = null): NetworkResponse<T,Nothing> {
            return Success(body,headers)
        }
        fun <U : Any> serverError( body: U?,  code: Int,  headers: Headers? = null, errorString:String = "", message: Int = 0): NetworkResponse<Nothing, U>{
            return ServerError(body,code,headers,errorString,message)
        }
        fun networkError(  error: IOException):NetworkResponse<Nothing, Nothing>{
            return NetworkError(error)
        }
    }


}

/**
 * A request that resulted in a response with a 2xx status code that has a body.
 */
data class Success<T : Any>(val body: T, val headers: Headers? = null) : NetworkResponse<T, Nothing>()

/**
 * A request that resulted in a response with a non-2xx status code.
 */
data class ServerError<U : Any>(val body: U?, val code: Int, val headers: Headers? = null,var errorString:String = "",val message: Int = 0) : NetworkResponse<Nothing, U>()

/**
 * A request that didn't result in a response.
 */
data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

