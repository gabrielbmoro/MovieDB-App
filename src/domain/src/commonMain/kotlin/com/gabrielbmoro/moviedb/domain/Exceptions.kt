package com.gabrielbmoro.moviedb.domain

data class HttpException(
    val statusCode: Int,
    val statusDescription: String,
    val requestUrl: String,
    val requestMethod: String
) : IllegalStateException()