package com.example.rawggames.domain.usecase

import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveFromFavoriteUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(detail: GameDetail): Flow<Boolean> = repository.removeFromFavorite(detail)
}