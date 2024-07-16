package com.gabrielbmoro.moviedb.details.ui.screens.details

import com.gabrielbmoro.moviedb.domain.entities.MovieDetail
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetMovieDetailsUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase

val fakeMovieDetail = MovieDetail(
    adult = false,
    budget = 1200,
    genres = listOf("genre1"),
    homepage = "homePage",
    imdbId = "imdbId",
    status = "status",
    tagline = "tagline",
    productionCompanies = listOf("productionCompany1"),
    overview = "overview",
    title = "title",
    votesAverage = 12f,
    releaseDate = "10/02/1990",
    backdropImageUrl = "backgropImageUrl",
    language = "language",
    popularity = 2f,
    posterImageUrl = "posterImageUrl"
)

class FakeFavoriteMovieUseCase : FavoriteMovieUseCase {
    override suspend fun execute(input: FavoriteMovieUseCase.Params) = Unit
}

class FakeIsFavoriteMovieUseCase : IsFavoriteMovieUseCase {
    var result: Boolean = false

    override suspend fun execute(input: IsFavoriteMovieUseCase.Params): Boolean {
        return result
    }
}

class FakeGetMovieDetailsUseCase : GetMovieDetailsUseCase {
    lateinit var result: MovieDetail

    override suspend fun execute(input: GetMovieDetailsUseCase.Params): MovieDetail {
        return result
    }
}
