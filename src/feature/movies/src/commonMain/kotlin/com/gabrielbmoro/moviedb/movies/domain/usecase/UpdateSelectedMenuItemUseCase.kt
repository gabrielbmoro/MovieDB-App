package com.gabrielbmoro.moviedb.movies.domain.usecase

import com.gabrielbmoro.moviedb.domain.usecases.UseCase
import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.model.FilterType

interface UpdateSelectedMenuItemUseCase :
    UseCase<UpdateSelectedMenuItemUseCase.Params, List<FilterMenuItem>> {

    data class Params(
        val items: List<FilterMenuItem>,
        val filterType: FilterType,
    )
}
