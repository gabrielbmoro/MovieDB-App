package com.gabrielbmoro.programmingchallenge.presentation

sealed class ViewModelResult {
    object Loading : ViewModelResult()
    object Success : ViewModelResult()
    object Updated : ViewModelResult()
    object Error : ViewModelResult()
}