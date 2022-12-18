package com.gabrielbmoro.programmingchallenge.domain.usecases

import com.gabrielbmoro.programmingchallenge.domain.model.ThemeType
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import javax.inject.Inject

class GetSelectedThemeUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    operator fun invoke(): ThemeType? {
        return repository.getCurrentTheme()
    }
}