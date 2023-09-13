package com.example.rawggames.domain.usecase

import com.example.rawggames.domain.model.Game
import com.example.rawggames.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGamesFavotiteUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(): Flow<List<Game>> = repository.getGameFavorite()
}