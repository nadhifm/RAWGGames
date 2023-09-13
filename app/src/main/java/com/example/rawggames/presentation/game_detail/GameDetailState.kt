package com.example.rawggames.presentation.game_detail

import com.example.rawggames.domain.model.GameDetail

data class GameDetailState(
    val gameId: Long = -1,
    val isLoading: Boolean = false,
    val gameDetail: GameDetail = GameDetail(
        -1,
        "",
        "",
        "",
        listOf(),
        listOf(),
        ""
    ),
    val isFavorite: Boolean = false,
    val isError: Boolean = false,
    val message: String = "",
)