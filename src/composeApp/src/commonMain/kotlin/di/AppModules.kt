package di

import com.gabrielbmoro.moviedb.data.di.dataModule
import com.gabrielbmoro.moviedb.details.di.featureDetailsModule
import com.gabrielbmoro.moviedb.domain.di.DomainModule
import com.gabrielbmoro.moviedb.logging.di.loggingModule
import com.gabrielbmoro.moviedb.movies.di.featureMoviesModule
import com.gabrielbmoro.moviedb.search.di.featureSearchMovieModule
import com.gabrielbmoro.moviedb.wishlist.di.featureWishlistModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.lazyModules
import org.koin.plugin.module.dsl.module

fun movieDbApplication(platformBlock: KoinApplication.() -> Unit): KoinApplication {
    return startKoin {
        platformBlock()
        modules(dataModule, loggingModule)

        module<DomainModule>()

        lazyModules(
            featureDetailsModule,
            featureMoviesModule,
            featureSearchMovieModule,
            featureWishlistModule,
        )
    }
}
