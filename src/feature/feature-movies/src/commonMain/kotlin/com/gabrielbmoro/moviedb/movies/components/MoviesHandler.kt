import com.gabrielbmoro.moviedb.domain.MoviesRepository
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.movies.ui.screens.movies.FilterType
import kotlin.collections.List

class MoviesHandler(
    private val repository: MoviesRepository,
) {
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
