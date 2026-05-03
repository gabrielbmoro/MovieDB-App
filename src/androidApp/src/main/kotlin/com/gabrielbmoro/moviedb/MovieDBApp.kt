package com.gabrielbmoro.moviedb

import android.app.Application
import di.movieDbApplication
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.android.ext.koin.androidContext

class MovieDBApp : Application() {
    override fun onCreate() {
        super.onCreate()

        movieDbApplication {
            androidContext(this@MovieDBApp)
            analytics()
        }
    }
}
