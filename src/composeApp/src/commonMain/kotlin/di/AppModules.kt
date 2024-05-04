package di

import com.gabrielbmoro.moviedb.BuildKonfig
import com.gabrielbmoro.moviedb.data.di.dataModule
import com.gabrielbmoro.moviedb.details.di.featureDetailsModule
import com.gabrielbmoro.moviedb.domain.di.domainModule
import com.gabrielbmoro.moviedb.movies.di.featureMoviesModule
import com.gabrielbmoro.moviedb.search.di.featureSearchMovieModule
import com.gabrielbmoro.moviedb.wishlist.di.featureWishlistModule
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
    single(named("api_token")) {
        BuildKonfig.API_TOKEN
    }
}

val appModules = listOf(
    authModule,
    dataModule,
    domainModule,
    dataModule,
    featureDetailsModule,
    featureMoviesModule,
    featureSearchMovieModule,
    featureWishlistModule
)
