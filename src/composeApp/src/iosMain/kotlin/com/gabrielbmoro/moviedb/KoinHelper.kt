package com.gabrielbmoro.moviedb

import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(di.appModules)
    }
}