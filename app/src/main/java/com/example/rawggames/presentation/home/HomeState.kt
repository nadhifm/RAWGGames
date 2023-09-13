package com.example.rawggames.presentation.home

import androidx.paging.PagingData
import com.example.rawggames.domain.model.Game
import kotlinx.coroutines.flow.MutableStateFlow

data class HomeState(
    val games: MutableStateFlow<PagingData<Game>> = MutableStateFlow(PagingData.empty()),
    val query: String = "",
)