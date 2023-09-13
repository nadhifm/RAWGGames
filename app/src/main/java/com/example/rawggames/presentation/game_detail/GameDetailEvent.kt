package com.example.rawggames.presentation.game_detail

sealed class GameDetailEvent {
    object RefreshGameDetail: GameDetailEvent()
    object OnFavoriteChange: GameDetailEvent()
}