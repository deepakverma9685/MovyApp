package com.example.moviez.utils

import android.util.Log
import com.example.moviez.BuildConfig

object LogUtils {

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        }
    }

    fun v(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message)
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message)
        }
    }

    fun logApi(url: String?, headers: String?, request: String?) {
        i(AppConstants.TAG_API, "URL : $url")
        i(AppConstants.TAG_API, "HEADERS : $headers")
        i(AppConstants.TAG_API, "REQUEST : $request")
    }

    fun logApi(response: String?) {
        i(AppConstants.TAG_API, "RESPONSE : $response")
    }
}