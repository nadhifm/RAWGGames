package com.example.rawggames.domain.usecase

import com.example.rawggames.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckIsFavoriteUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(id: Long): Flow<Boolean> = repository.checkIsFavorite(id)
}