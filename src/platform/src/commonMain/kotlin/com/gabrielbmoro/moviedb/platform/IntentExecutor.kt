package com.gabrielbmoro.moviedb.platform

interface IntentExecutor<in UserIntent : Any> {
    fun execute(intent: UserIntent)
}
