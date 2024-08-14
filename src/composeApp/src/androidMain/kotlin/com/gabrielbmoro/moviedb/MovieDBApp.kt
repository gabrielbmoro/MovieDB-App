package com.gabrielbmoro.moviedb

import android.app.Application
import di.appModules
import di.featureModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.lazyModules

class MovieDBApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieDBApp)
            modules(appModules)
            lazyModules(featureModules)
        }
    }
}
