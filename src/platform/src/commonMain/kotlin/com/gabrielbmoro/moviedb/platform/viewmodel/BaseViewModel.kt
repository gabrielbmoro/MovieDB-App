package com.gabrielbmoro.moviedb.platform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Intent : UserIntent, Event : UiEvent>(
    private val ioCoroutinesDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val internalUiState = MutableStateFlow(this.defaultEmptyState())
    val uiState: StateFlow<State> = internalUiState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        internalUiState.value,
    )

    private val internalUiEvent = MutableSharedFlow<Event>()
    val uiEvent: SharedFlow<Event> = internalUiEvent

    abstract fun executeIntent(intent: Intent)

    abstract fun defaultEmptyState(): State

    protected suspend fun updateState(update: (State) -> State) {
        val newState = update(uiState.value)
        internalUiState.emit(newState)
    }

    protected suspend fun fireEvent(event: Event) {
        internalUiEvent.emit(event)
    }

    protected fun launchIo(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(ioCoroutinesDispatcher) {
            runCatching {
                block()
            }.onFailure {
                onFailure(it)
            }
        }
    }

    abstract fun onFailure(throwable: Throwable)
}
