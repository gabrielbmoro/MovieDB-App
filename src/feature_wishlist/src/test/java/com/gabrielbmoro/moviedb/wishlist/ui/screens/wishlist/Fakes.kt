package com.gabrielbmoro.moviedb.wishlist.ui.screens.wishlist

import com.gabrielbmoro.moviedb.core.providers.resources.ResourcesProvider
import com.gabrielbmoro.moviedb.domain.entities.Movie
import com.gabrielbmoro.moviedb.domain.usecases.FavoriteMovieUseCase
import com.gabrielbmoro.moviedb.domain.usecases.GetFavoriteMoviesUseCase
import com.gabrielbmoro.moviedb.domain.usecases.IsFavoriteMovieUseCase

class FakeGetFavoriteMoviesUseCase : GetFavoriteMoviesUseCase {
    lateinit var result: List<Movie>

    override suspend fun execute(input: Unit): List<Movie> {
        return result
    }
}

class FakeFavoriteMovieUseCase : FavoriteMovieUseCase {
    var timesCalled: Int = 0
        private set

    override suspend fun execute(input: FavoriteMovieUseCase.Params) {
        timesCalled++
    }
}

class FakeIsFavoriteMovieUseCase : IsFavoriteMovieUseCase {
    var result: Boolean = false

    override suspend fun execute(input: IsFavoriteMovieUseCase.Params): Boolean {
        return result
    }
}

class FakeResourcesProvider : ResourcesProvider {
    lateinit var stringResult: String

    lateinit var arrayResult: Array<String>

    override fun getArray(arrayRes: Int): Array<String> {
        return arrayResult
    }

    override fun getString(stringRes: Int): String {
        return stringResult
    }
}