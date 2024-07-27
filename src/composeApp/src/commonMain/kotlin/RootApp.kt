import androidx.compose.runtime.Composable
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

@Composable
fun RootApp() {
    val navigator = rememberNavController()

    NavHost(
        startDestination = Screen.Movies.route,
        navController = navigator,
    ) {
        addMoviesScreen { MoviesScreen(navigator = navigator) }
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
        addSearchScreen { query -> SearchScreen(query = null, navigator = navigator) }
    }
}
