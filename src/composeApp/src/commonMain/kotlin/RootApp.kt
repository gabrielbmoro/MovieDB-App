import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gabrielbmoro.moviedb.feature.details.ui.screens.details.DetailsScreen
import com.gabrielbmoro.moviedb.feature.movies.ui.screens.movies.MoviesScreen
import com.gabrielbmoro.moviedb.platform.LocalNavController
import com.gabrielbmoro.moviedb.platform.navigation.Screen
import com.gabrielbmoro.moviedb.platform.navigation.addMovieDetailsScreen
import com.gabrielbmoro.moviedb.platform.navigation.addMoviesScreen
import com.gabrielbmoro.moviedb.platform.navigation.addSearchScreen
import com.gabrielbmoro.moviedb.platform.navigation.addWishlistScreen
import com.gabrielbmoro.moviedb.search.ui.screens.search.SearchScreen
import com.gabrielbmoro.moviedb.feature.wishlist.ui.screens.wishlist.WishlistScreen

@Composable
fun RootApp() {
    val navigator = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navigator) {
        NavHost(
            startDestination = Screen.Movies.route,
            navController = navigator,
        ) {
            addMoviesScreen {
                MoviesScreen()
            }

            addMovieDetailsScreen { movieId ->
                DetailsScreen(
                    movieId = movieId,
                )
            }
            addWishlistScreen {
                WishlistScreen()
            }
            addSearchScreen { query -> SearchScreen(query = query) }
        }

        DeeplinkEffect()
    }
}
