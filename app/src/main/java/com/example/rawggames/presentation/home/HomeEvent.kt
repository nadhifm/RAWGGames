package com.example.rawggames.presentation.home

sealed class HomeEvent {
    data class OnSearchQueryChange(val query: String): HomeEvent()
}