package com.gabrielbmoro.moviedb.platform.di

import com.gabrielbmoro.moviedb.platform.logging.LoggerHelper
import com.gabrielbmoro.moviedb.platform.logging.LoggerHelperImpl
import org.koin.dsl.module

val platformModule = module {
    factory<LoggerHelper> {
        LoggerHelperImpl()
    }
}
