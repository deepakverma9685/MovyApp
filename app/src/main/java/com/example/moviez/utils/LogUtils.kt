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
        i("BWAPI", "URL : $url")
        i("BWAPI", "HEADERS : $headers")
        i("BWAPI", "REQUEST : $request")
    }

    fun logApi(response: String?) {
        i("TAG", "RESPONSE : $response")
    }
}