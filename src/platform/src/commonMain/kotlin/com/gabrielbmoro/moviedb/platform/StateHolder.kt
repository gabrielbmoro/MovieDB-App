package com.gabrielbmoro.moviedb.platform

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

abstract class StateHolder<GenericState, SuccessState: GenericState>(
    firstState: GenericState,
) {
    private val _state: MutableStateFlow<GenericState> = MutableStateFlow(firstState)
    val state: StateFlow<GenericState> = _state

    fun updateState(newValue: GenericState) {
        _state.update { newValue }
    }
}
