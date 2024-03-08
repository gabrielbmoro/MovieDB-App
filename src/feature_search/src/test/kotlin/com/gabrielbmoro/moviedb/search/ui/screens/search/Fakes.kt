package com.gabrielbmoro.moviedb.search.ui.screens.search

import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.SearchMovieUseCase

class FakeSearchUseCase : SearchMovieUseCase {

    lateinit var searchResult: List<Movie>

    override suspend fun execute(input: SearchMovieUseCase.Params): List<Movie> {
        return searchResult
    }
}