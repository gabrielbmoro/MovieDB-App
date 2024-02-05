package com.gabrielbmoro.moviedb.core.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModelMVI <in Intent: Any, State: Any>: ViewModel() {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    protected abstract suspend fun execute(intent: Intent): State

    protected abstract fun defaultEmptyState(): State

    fun accept(intent: Intent) {
        viewModelScope.launch {
            val state = execute(intent)
            _uiState.update { state }
        }
    }

    protected fun updateState(state: State) {
        _uiState.update { state }
    }

    protected fun getState() : State{
        return _uiState.value
    }
}
