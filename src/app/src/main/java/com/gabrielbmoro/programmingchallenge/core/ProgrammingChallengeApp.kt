package com.gabrielbmoro.programmingchallenge.core

import android.app.Application
import com.gabrielbmoro.programmingchallenge.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * The base context.
 * @author Gabriel Moro
 * @since 2018-08-30
 */
class ProgrammingChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ProgrammingChallengeApp)
            koin.loadModules(listOf(repositoryModule, usecaseModule, viewModelModules))
            koin.createRootScope()
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}