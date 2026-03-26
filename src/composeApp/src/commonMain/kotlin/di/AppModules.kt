package di

import com.gabrielbmoro.moviedb.details.di.featureDetailsModule
import com.gabrielbmoro.moviedb.logging.di.loggingModule
import com.gabrielbmoro.moviedb.movies.di.featureMoviesModule
import com.gabrielbmoro.moviedb.search.di.featureSearchMovieModule
import com.gabrielbmoro.moviedb.wishlist.di.featureWishlistModule

val appModules = listOf(
    loggingModule,
)

val featureModules = listOf(
    featureDetailsModule,
    featureMoviesModule,
    featureSearchMovieModule,
    featureWishlistModule,
)
