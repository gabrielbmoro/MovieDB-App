package com.gabrielbmoro.moviedb.platform.mvi

import ModelViewIntent
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ScreenModelMVI<in UserIntent : Any, ScreenState : Any> : ScreenModel,
    ModelViewIntent<UserIntent, ScreenState> {

    private val _uiState = MutableStateFlow(this.defaultEmptyState())
    val uiState = _uiState.stateIn(screenModelScope, SharingStarted.Eagerly, _uiState.value)

    init {
        screenModelScope.launch(Dispatchers.IO) {
            val state = setup()
            if (state != null) {
                _uiState.update { state }
            }
        }
    }

    fun accept(intent: UserIntent) {
        screenModelScope.launch(Dispatchers.IO) {
            val state = execute(intent)
            _uiState.update { state }
        }
    }

    protected fun updateState(state: ScreenState) {
        _uiState.update { state }
    }

    protected fun getState(): ScreenState {
        return _uiState.value
    }

    override suspend fun setup(): ScreenState? {
        return null
    }
}