package com.gabrielbmoro.moviedb.movies.domain.usecase.impl

import com.gabrielbmoro.moviedb.movies.domain.model.FilterMenuItem
import com.gabrielbmoro.moviedb.movies.domain.usecase.UpdateSelectedMenuItemUseCase

class UpdateSelectedMenuItemUseCaseImpl : UpdateSelectedMenuItemUseCase {

    override suspend fun execute(
        input: UpdateSelectedMenuItemUseCase.Params,
    ): List<FilterMenuItem> = input.run {
        items.map {
            it.copy(selected = it.type == filterType)
        }
    }
}
