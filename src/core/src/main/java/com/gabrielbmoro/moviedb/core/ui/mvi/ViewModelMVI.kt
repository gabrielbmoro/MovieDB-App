package com.gabrielbmoro.moviedb.core.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ViewModelMVI<in UserIntent : Any, ScreenState : Any, Label : Any> : ViewModel() {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    private val _label = MutableSharedFlow<Label?>(0)
    val label: SharedFlow<Label?> = _label

    protected abstract suspend fun execute(intent: UserIntent): ScreenState

    protected abstract fun defaultEmptyState(): ScreenState

    fun accept(intent: UserIntent) {
        viewModelScope.launch {
            val state = execute(intent)
            _uiState.update { state }
        }
    }

    suspend fun publish(label: Label) {
        _label.emit(label)
    }

    protected fun updateState(state: ScreenState) {
        _uiState.update { state }
    }

    protected fun getState(): ScreenState {
        return _uiState.value
    }
}
