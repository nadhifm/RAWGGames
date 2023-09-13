package com.example.rawggames.presentation.game_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.domain.usecase.CheckIsFavoriteUseCase
import com.example.rawggames.domain.usecase.GetGameDetailUseCase
import com.example.rawggames.domain.usecase.RemoveFromFavoriteUseCase
import com.example.rawggames.domain.usecase.SaveToFavoriteUseCase
import com.example.rawggames.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameDetailUseCase: GetGameDetailUseCase,
    private val saveToFavoriteUseCase: SaveToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val checkIsFavoriteUseCase: CheckIsFavoriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(GameDetailState())

    init {
        savedStateHandle.get<Long>("gameId")?.let { gameId ->
            if (gameId != (-1).toLong()) {
                state = state.copy(
                    gameId = gameId
                )
                checkFavorite()
                getGameDetail()
            }
        }
    }

    fun onEvent(event: GameDetailEvent) {
        when(event) {
            is GameDetailEvent.RefreshGameDetail -> {
                getGameDetail()
            }
            is GameDetailEvent.OnFavoriteChange -> {
                if (state.isFavorite) {
                    removeFromFavorite()
                } else {
                    saveToFavorite()
                }
            }
        }
    }

    private fun checkFavorite(id: Long = state.gameId) {
        viewModelScope.launch {
            checkIsFavoriteUseCase.invoke(id).collect {
                state = state.copy(
                    isFavorite = it
                )
            }
        }
    }

    private fun saveToFavorite(gameDetail: GameDetail = state.gameDetail) {
        viewModelScope.launch {
            saveToFavoriteUseCase.invoke(gameDetail).collect {
                state = state.copy(
                    isFavorite = it
                )
            }
        }
    }

    private fun removeFromFavorite(gameDetail: GameDetail = state.gameDetail) {
        viewModelScope.launch {
            removeFromFavoriteUseCase.invoke(gameDetail).collect {
                state = state.copy(
                    isFavorite = it
                )
            }
        }
    }

    private fun getGameDetail(id: Long = state.gameId) {
        viewModelScope.launch {
            getGameDetailUseCase.invoke(id)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { gameDetail ->
                                state = state.copy(
                                    isError = false,
                                    isLoading = false,
                                    gameDetail = gameDetail,
                                    message = ""
                                )
                            }
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false,
                                isError = true,
                                message = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isError = false,
                                isLoading = result.isLoading,
                                message = ""
                            )
                        }
                    }
                }
        }
    }
}