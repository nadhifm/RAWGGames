package com.example.rawggames.domain.repository

import androidx.paging.PagingData
import com.example.rawggames.domain.model.Game
import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(query: String): Flow<PagingData<Game>>
    fun getGameDetail(id: Long): Flow<Resource<GameDetail>>
    fun saveToFavorite(detail: GameDetail): Flow<Boolean>
    fun removeFromFavorite(detail: GameDetail): Flow<Boolean>
    fun getGameFavorite(): Flow<List<Game>>
    fun checkIsFavorite(id: Long): Flow<Boolean>
}