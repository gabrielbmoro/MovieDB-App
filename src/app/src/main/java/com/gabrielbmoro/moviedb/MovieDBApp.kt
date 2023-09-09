package com.gabrielbmoro.moviedb

import android.app.Application
import com.gabrielbmoro.moviedb.core.di.CoreModule
import com.gabrielbmoro.moviedb.details.di.DetailsModule
import com.gabrielbmoro.moviedb.movies.di.MoviesModule
import com.gabrielbmoro.moviedb.repository.di.RepositoryModule
import com.gabrielbmoro.moviedb.wishlist.di.WishlistModule
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

            modules(
                CoreModule.providersModule,
                DetailsModule.module,
                WishlistModule.module,
                MoviesModule.module,
                RepositoryModule(BuildConfig.API_TOKEN).module,
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}