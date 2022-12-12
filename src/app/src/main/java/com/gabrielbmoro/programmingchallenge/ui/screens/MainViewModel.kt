package com.gabrielbmoro.programmingchallenge.ui.screens

import androidx.lifecycle.ViewModel
import com.gabrielbmoro.programmingchallenge.domain.model.ThemeType
import com.gabrielbmoro.programmingchallenge.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MoviesRepository,
) : ViewModel() {

    fun themeType(): ThemeType {
        return repository.getCurrentTheme() ?: ThemeType.AUTOMATIC
    }
}