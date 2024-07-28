import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.gabrielbmoro.moviedb.platform.navigation.SEARCH_QUERY_ARGUMENT_KEY
import com.gabrielbmoro.moviedb.platform.navigation.Screen
import com.gabrielbmoro.moviedb.platform.navigation.detailsRoute
import com.gabrielbmoro.moviedb.platform.navigation.searchRoute
import dev.theolm.rinku.compose.ext.DeepLinkListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun DeeplinkEffect(
    deepLinkBarrier: MutableStateFlow<String?>
) {
    val coroutineContext = rememberCoroutineScope()

    DeepLinkListener {
        when (it.pathSegments.firstOrNull()) {
            Screen.Details.firstSegment -> {
                it.pathSegments.getOrNull(1)?.split("-")?.firstOrNull()?.toLongOrNull()
                    ?.let { movieId ->
                        coroutineContext.launch {
                            deepLinkBarrier.emit(
                                Screen.Details.route.detailsRoute(movieId)
                            )
                        }
                    }
            }

            Screen.Wishlist.firstSegment -> {
                coroutineContext.launch {
                    deepLinkBarrier.emit(Screen.Wishlist.route)
                }
            }

            Screen.Search.firstSegment -> {
                it.parameters[SEARCH_QUERY_ARGUMENT_KEY]?.let { query ->
                    coroutineContext.launch {
                        deepLinkBarrier.emit(
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
