package com.example.rawggames.domain.usecase

import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.domain.repository.GameRepository
import com.example.rawggames.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameDetailUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(id: Long): Flow<Resource<GameDetail>> = repository.getGameDetail(id)
}