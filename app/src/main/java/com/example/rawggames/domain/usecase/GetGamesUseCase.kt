package com.example.rawggames.domain.usecase

import androidx.paging.PagingData
import com.example.rawggames.domain.model.Game
import com.example.rawggames.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Game>> = repository.getGames(query)
}