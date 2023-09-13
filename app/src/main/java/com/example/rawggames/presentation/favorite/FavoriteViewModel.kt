package com.example.rawggames.presentation.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rawggames.domain.usecase.GetGamesFavotiteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getGamesFavotiteUseCase: GetGamesFavotiteUseCase,
) : ViewModel() {
    var state by mutableStateOf(FavoriteState())

    init {
        getGamesFavorite()
    }

    fun onEvent(event: FavoriteEvent) {
        when(event) {
            is FavoriteEvent.GetGamesFavorite -> {
                getGamesFavorite()
            }
        }
    }

    private fun getGamesFavorite() {
        viewModelScope.launch {
            getGamesFavotiteUseCase.invoke()
                .collect { result ->
                   state = state.copy(
                       games = result
                   )
                }
        }
    }
}