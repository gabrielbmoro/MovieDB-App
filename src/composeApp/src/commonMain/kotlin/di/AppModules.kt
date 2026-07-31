package di

import com.gabrielbmoro.moviedb.data.di.dataModule
import com.gabrielbmoro.moviedb.domain.di.DomainModule
import com.gabrielbmoro.moviedb.feature.movies.di.featureMoviesModule
import com.gabrielbmoro.moviedb.feature.tvshows.di.featureTvShowsModule
import com.gabrielbmoro.moviedb.feature.wishlist.di.featureWishlistModule
import com.gabrielbmoro.moviedb.platform.di.platformModule
import com.gabrielbmoro.moviedb.search.di.featureSearchMovieModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.lazyModules
import org.koin.plugin.module.dsl.module

fun movieDbApplication(platformBlock: KoinApplication.() -> Unit): KoinApplication {
    return startKoin {
        platformBlock()
        modules(dataModule, platformModule)

        module<DomainModule>()

        lazyModules(
            featureMoviesModule,
            featureTvShowsModule,
            featureSearchMovieModule,
            featureWishlistModule,
        )
    }
}
