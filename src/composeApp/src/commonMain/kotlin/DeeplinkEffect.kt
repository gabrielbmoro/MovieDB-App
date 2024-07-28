import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.gabrielbmoro.moviedb.platform.navigation.SEARCH_QUERY_ARGUMENT_KEY
import com.gabrielbmoro.moviedb.platform.navigation.Screen
import com.gabrielbmoro.moviedb.platform.navigation.detailsRoute
import com.gabrielbmoro.moviedb.platform.navigation.searchRoute
import dev.theolm.rinku.compose.ext.DeepLinkListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun DeeplinkEffect(
    navigator: NavHostController
) {
    val deepLinkNavigation = remember { DeepLinkNavigation() }
    val coroutineContext = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        deepLinkNavigation.barrier.collectLatest { route ->
            route?.let {
                navigator.navigate(route)
            }
        }
    }

    DeepLinkListener {
        when (it.pathSegments.firstOrNull()) {
            Screen.Details.firstSegment -> {
                it.pathSegments.getOrNull(1)?.split("-")?.firstOrNull()?.toLongOrNull()
                    ?.let { movieId ->
                        coroutineContext.launch {
                            deepLinkNavigation.barrier.emit(
                                Screen.Details.route.detailsRoute(movieId)
                            )
                        }
                    }
            }

            Screen.Wishlist.firstSegment -> {
                coroutineContext.launch {
                    deepLinkNavigation.barrier.emit(Screen.Wishlist.route)
                }
            }

            Screen.Search.firstSegment -> {
                it.parameters[SEARCH_QUERY_ARGUMENT_KEY]?.let { query ->
                    coroutineContext.launch {
                        deepLinkNavigation.barrier.emit(
                            Screen.Search.route.searchRoute(query)
                        )
                    }
                }
            }

            else -> {
                println("Not mapped")
            }
        }
    }
}

class DeepLinkNavigation {
    val barrier = MutableStateFlow<String?>(null)
}
