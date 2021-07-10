package com.gabrielbmoro.programmingchallenge.core

import android.app.Application
import com.gabrielbmoro.programmingchallenge.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class ProgrammingChallengeApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}