package com.gabrielbmoro.moviedb.data.providers

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine

actual fun httpClientEngine(): HttpClientEngine{
    return OkHttpEngine(OkHttpConfig())
}