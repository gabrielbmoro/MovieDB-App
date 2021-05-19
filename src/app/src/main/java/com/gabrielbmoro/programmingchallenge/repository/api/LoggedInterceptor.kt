package com.gabrielbmoro.programmingchallenge.repository.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggedInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("NETWORK_REQUEST", "${chain.request().method()}:${chain.request().url()}<--")
        val response = chain.proceed(chain.request())
        Log.d("NETWORK_RESPONSE", "-->$response")
        return response
    }
}