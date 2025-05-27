import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.model.FilterType
import com.gabrielbmoro.moviedb.movies.model.MoviesState
import kotlinx.collections.immutable.persistentListOf
import kotlin.collections.List

class MoviesHandler(
    private val repository: MoviesRepository,
) {
    val menuItems = listOf(
        FilterMenuItem(
            selected = true,
            type = FilterType.NowPlaying,
        ),
        FilterMenuItem(
            selected = false,
            type = FilterType.UpComing,
        ),
        FilterMenuItem(
            selected = false,
            type = FilterType.TopRated,
        ),
        FilterMenuItem(
            selected = false,
            type = FilterType.Popular,
        ),
    )

    val defaultEmptyState = MoviesState(
        movieCardInfos = persistentListOf(),
        selectedFilterMenu = FilterType.NowPlaying,
        menuItems = menuItems,
        isLoading = false,
    )

    suspend fun getMoviesFromFilter(filter: FilterType, page: Int): List<Movie> {
        return repository.getMoviesFromFilter(
            filter = filter.asString(),
            page = page,
        )
    }

    private fun FilterType.asString(): String = when (this) {
        FilterType.Popular -> "popular"
        FilterType.TopRated -> "top_rated"
        FilterType.UpComing -> "upcoming"
        FilterType.NowPlaying -> "now_playing"
    }
}
