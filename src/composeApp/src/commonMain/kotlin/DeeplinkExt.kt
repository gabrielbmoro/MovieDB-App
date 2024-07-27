import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform

//private val DefaultStack = listOf(MoviesScreen())

/*fun DeepLink.toScreenStack(): List<Screen> {
    if (this.pathSegments.isEmpty()) {
        return DefaultStack
    }
    val firstSegment = this.pathSegments.firstOrNull()
    return when (firstSegment) {
        "favorite" -> {
            val wishListScreen =
                KoinPlatform.getKoin()
                    .get<Screen>(named(NavigationDestinations.WISHLIST))
            DefaultStack + wishListScreen
        }
        "search" -> {
            val query = this.parameters["query"]
            val searchScreen = KoinPlatform.getKoin().get<Screen>(
                qualifier = named(NavigationDestinations.SEARCH),
                parameters = { parametersOf(query) }
            )

            DefaultStack + searchScreen
        }

        "movie" -> {
            val movieId = this.pathSegments.tryGetMovieId()
            if (movieId != null) {
                val detailsScreen = KoinPlatform.getKoin().get<Screen>(
                    qualifier = named(NavigationDestinations.DETAILS),
                    parameters = { parametersOf(movieId) }
                )
                DefaultStack + detailsScreen
            } else {
                DefaultStack
            }
        }

        else -> DefaultStack
    }
}

private fun List<String>.tryGetMovieId() =
    this.getOrNull(1)?.split("-")?.firstOrNull()?.toLongOrNull()
*/
