package com.gabrielbmoro.moviedb

import org.koin.core.context.startKoin
import org.koin.core.lazyModules

fun initKoin() {
    startKoin {
        modules(di.appModules)
        lazyModules(di.featureModules)
    }
}
