import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.moviedb.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.platform.navigation.Screen
import com.gabrielbmoro.moviedb.platform.navigation.addMovieDetailsScreen
import com.gabrielbmoro.moviedb.platform.navigation.addMoviesScreen
import com.gabrielbmoro.moviedb.platform.navigation.addSearchScreen
import com.gabrielbmoro.moviedb.platform.navigation.addWishlistScreen
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchScreen
import com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist.WishlistScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RootApp() {
    val navigator = rememberNavController()

    val deepLinkNavigation = remember { DeepLinkNavigation() }

    NavHost(
        startDestination = Screen.Movies.route,
        navController = navigator,
    ) {
        addMoviesScreen {
            MoviesScreen(navigator = navigator)

            // Defined deeplink barrier to the first screen destination
            LaunchedEffect(Unit) {
                deepLinkNavigation.barrier.collectLatest { route ->
                    route?.let {
                        navigator.navigate(it)
                    }
                }
            }
        }

        addMovieDetailsScreen { movieId ->
            DetailsScreen(
                movieId = movieId,
                navigator = navigator
            )
        }
        addWishlistScreen {
            WishlistScreen(
                navigator = navigator
            )
        }
        addSearchScreen { query -> SearchScreen(query = query, navigator = navigator) }
    }

    DeeplinkEffect(
        deepLinkBarrier = deepLinkNavigation.barrier,
    )
}

class DeepLinkNavigation {
    val barrier = MutableStateFlow<String?>(null)
}
