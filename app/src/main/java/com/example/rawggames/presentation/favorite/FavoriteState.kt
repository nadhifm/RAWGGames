package com.example.rawggames.presentation.favorite

import com.example.rawggames.domain.model.Game

data class FavoriteState(
    val games: List<Game> = listOf()
)
