package com.gabrielbmoro.moviedb.logging.di

import com.gabrielbmoro.moviedb.logging.LoggerHelper
import com.gabrielbmoro.moviedb.logging.LoggerHelperImpl
import org.koin.dsl.module

val loggingModule = module {
    factory<LoggerHelper> {
        LoggerHelperImpl()
    }
}
