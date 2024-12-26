package com.gabrielbmoro.moviedb.logging

import co.touchlab.kermit.Logger
import kotlin.reflect.KClass

interface LoggerHelper {
    fun plant(baseClass: KClass<*>)
    fun logDebug(message: String)
    fun logInfo(message: String)
    fun logError(error: Throwable)
}

internal class LoggerHelperImpl : LoggerHelper {

    private var _tag: String? = null

    override fun plant(baseClass: KClass<*>) {
        _tag = baseClass.simpleName.orEmpty()
    }

    override fun logDebug(message: String) {
        Logger.d(_tag.orEmpty()) {
            message
        }
    }

    override fun logInfo(message: String) {
        Logger.i(_tag.orEmpty()) {
            message
        }
    }

    override fun logError(error: Throwable) {
        Logger.e(_tag.orEmpty(), error)
    }
}
