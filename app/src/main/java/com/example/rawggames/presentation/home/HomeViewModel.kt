package com.example.rawggames.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rawggames.domain.model.Game
import com.example.rawggames.domain.usecase.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {
    var state by mutableStateOf(HomeState())

    private var searchJob: Job? = null

    init {
        getGames()
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.OnSearchQueryChange -> {
                state = state.copy(query = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getGames()
                }
            }
        }
    }

    private fun getGames(query: String = state.query) {
        getGamesUseCase.invoke(query)
            .cachedIn(viewModelScope)
            .onEach {
                state.games.value = it
            }
            .launchIn(viewModelScope)
    }
}