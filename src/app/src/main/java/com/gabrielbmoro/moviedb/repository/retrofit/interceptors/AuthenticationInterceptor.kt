package com.gabrielbmoro.moviedb.repository.retrofit.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val apiToken: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $apiToken"
            )
            .build()

        val response = chain.proceed(request)
        return response
    }
}