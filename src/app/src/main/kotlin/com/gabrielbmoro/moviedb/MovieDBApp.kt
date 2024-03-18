package com.gabrielbmoro.moviedb

import android.app.Application
import com.gabrielbmoro.moviedb.core.di.coreModule
import com.gabrielbmoro.moviedb.details.di.featureDetailsModule
import com.gabrielbmoro.moviedb.di.appModule
import com.gabrielbmoro.moviedb.domain.di.domainModule
import com.gabrielbmoro.moviedb.movies.di.featureMoviesModule
import com.gabrielbmoro.moviedb.repository.datasources.room.DataBaseFactory
import com.gabrielbmoro.moviedb.repository.di.dataModule
import com.gabrielbmoro.moviedb.search.di.featureSearchMovieModule
import com.gabrielbmoro.moviedb.wishlist.di.featureWishlistModule
import org.koin.core.context.startKoin
import timber.log.Timber

class MovieDBApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            modules(
                appModule,
                coreModule,
                domainModule,
                dataModule(this@MovieDBApp),
                featureDetailsModule,
                featureMoviesModule,
                featureSearchMovieModule,
                featureWishlistModule
            )
        }
    }
}
