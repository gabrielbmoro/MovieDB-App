package com.gabrielbmoro.moviedb.data.providers

import io.ktor.client.engine.HttpClientEngine

expect fun httpClientEngine(): HttpClientEngine
