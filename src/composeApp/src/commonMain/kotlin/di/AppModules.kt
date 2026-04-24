package di

import com.gabrielbmoro.moviedb.data.di.dataModule
import com.gabrielbmoro.moviedb.details.di.featureDetailsModule
import com.gabrielbmoro.moviedb.domain.di.DomainModule
import com.gabrielbmoro.moviedb.logging.di.loggingModule
import com.gabrielbmoro.moviedb.movies.di.featureMoviesModule
import com.gabrielbmoro.moviedb.search.di.featureSearchMovieModule
import com.gabrielbmoro.moviedb.wishlist.di.featureWishlistModule
import org.koin.ksp.generated.module

val appModules = listOf(
    loggingModule,
    dataModule,
    DomainModule().module,
)

val featureModules = listOf(
    featureDetailsModule,
    featureMoviesModule,
    featureSearchMovieModule,
    featureWishlistModule,
)
