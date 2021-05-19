package com.gabrielbmoro.programmingchallenge.presentation.viewModels

import com.gabrielbmoro.programmingchallenge.KoinUnitTest
import com.gabrielbmoro.programmingchallenge.domain.model.Movie
import com.gabrielbmoro.programmingchallenge.domain.usecase.CheckMovieIsFavoriteUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.FavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.domain.usecase.UnFavoriteMovieUseCase
import com.gabrielbmoro.programmingchallenge.presentation.detailedScreen.MovieDetailedViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.koin.test.inject

class MovieDetailedViewModelTest : KoinUnitTest() {

    private val favoriteMovieUseCase by inject<FavoriteMovieUseCase>()
    private val unFavoriteMovieUseCase by inject<UnFavoriteMovieUseCase>()
    private val checkMovieIsFavoriteUseCase by inject<CheckMovieIsFavoriteUseCase>()

    private fun emptyMovieObj(): Movie {
        return Movie(
                id = null,
                votes = 0,
                isVideo = false,
                votesAverage = 0f,
                title = "",
                popularity = 0f,
                posterPath = "",
                originalLanguage = "",
                originalTitle = "",
                backdropPath = "",
                isAdult = false,
                overview = "",
                releaseDate = "",
                isFavorite = false
        )
    }

    @Test
    fun `movie can be selected as favorite`() {
        // given
        val useCaseSpy = spyk(favoriteMovieUseCase)
        val movie = emptyMovieObj()
        val viewModel = MovieDetailedViewModel(
                movie,
                useCaseSpy,
                unFavoriteMovieUseCase,
                checkMovieIsFavoriteUseCase
        )

        GlobalScope.launch {
            // when
            viewModel.isToFavoriteOrUnFavorite(true)

            // then
            coVerify {
                useCaseSpy.execute(movie)
            }
        }
    }

    @Test
    fun `movie can be selected as unFavorite`() {
        // given
        val useCaseSpy = spyk(unFavoriteMovieUseCase)
        val movie = emptyMovieObj()
        val viewModel = MovieDetailedViewModel(
                movie,
                favoriteMovieUseCase,
                useCaseSpy,
                checkMovieIsFavoriteUseCase
        )

        GlobalScope.launch {
            // when
            viewModel.isToFavoriteOrUnFavorite(false)

            //then
            coVerify {
                useCaseSpy.execute(movie)
            }
        }
    }

    @Test
    fun `movie can be selected`() {
        // given
        val movieId = -1
        val votes = 4
        val isVideo = false
        val votesAverage = 2f
        val title = "Ad Astra"
        val popularity = 2f
        val posterPath = ""
        val originalLanguage = "en-US"
        val backdropPath = ""
        val isAdult = false
        val overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, " +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
                "sunt in culpa qui officia deserunt mollit anim id est laborum."
        val releaseDate = "02-02-2012"
        val isFavorite = true
        val movie = Movie(
                id = movieId,
                votes = votes,
                isVideo = isVideo,
                votesAverage = votesAverage,
                title = title,
                popularity = popularity,
                posterPath = posterPath,
                originalLanguage = originalLanguage,
                originalTitle = title,
                backdropPath = backdropPath,
                isAdult = isAdult,
                overview = overview,
                releaseDate = releaseDate,
                isFavorite = isFavorite
        )

        // when
        val viewModel = MovieDetailedViewModel(
                movie,
                favoriteMovieUseCase,
                unFavoriteMovieUseCase,
                checkMovieIsFavoriteUseCase
        )

        // then
        assertThat(viewModel.movie.id).isEqualTo(movieId)
        assertThat(viewModel.movie.votes).isEqualTo(votes)
        assertThat(viewModel.movie.isVideo).isEqualTo(isVideo)
        assertThat(viewModel.movie.votesAverage).isEqualTo(votesAverage)
        assertThat(viewModel.movie.title).isEqualTo(title)
        assertThat(viewModel.movie.originalLanguage).isEqualTo(originalLanguage)
        assertThat(viewModel.movie.originalTitle).isEqualTo(title)
        assertThat(viewModel.movie.popularity).isEqualTo(popularity)
        assertThat(viewModel.movie.posterPath).isEqualTo(posterPath)
        assertThat(viewModel.movie.backdropPath).isEqualTo(backdropPath)
        assertThat(viewModel.movie.isAdult).isEqualTo(isAdult)
        assertThat(viewModel.movie.overview).isEqualTo(overview)
        assertThat(viewModel.movie.releaseDate).isEqualTo(releaseDate)
        assertThat(viewModel.movie.isFavorite).isEqualTo(isFavorite)
    }

}