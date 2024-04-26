package com.gabrielbmoro.moviedb

import android.app.Application
import di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieDBApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieDBApp)
            modules(appModules)
        }
    }
}
