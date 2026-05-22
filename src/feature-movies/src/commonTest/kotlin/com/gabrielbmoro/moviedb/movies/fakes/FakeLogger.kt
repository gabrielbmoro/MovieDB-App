package com.gabrielbmoro.moviedb.movies.fakes

import com.gabrielbmoro.moviedb.logging.LoggerHelper
import kotlin.reflect.KClass

class FakeLogger : LoggerHelper {
    override fun plant(baseClass: KClass<*>) = Unit
    override fun logDebug(message: String) = Unit
    override fun logInfo(message: String) = Unit
    override fun logError(error: Throwable) = Unit
}
