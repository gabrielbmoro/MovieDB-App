package com.gabrielbmoro.moviedb.core

import android.app.Application
import com.gabrielbmoro.moviedb.BuildConfig
import com.gabrielbmoro.moviedb.core.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class MovieDBApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieDBApp.applicationContext)
            androidLogger()
            modules(AppModule.list)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}