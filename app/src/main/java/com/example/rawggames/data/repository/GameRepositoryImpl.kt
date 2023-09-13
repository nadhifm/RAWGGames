package com.example.rawggames.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rawggames.data.local.GameDao
import com.example.rawggames.data.local.entity.GameEntity
import com.example.rawggames.data.mapper.toGame
import com.example.rawggames.data.mapper.toGameDetail
import com.example.rawggames.data.mapper.toGameEntity
import com.example.rawggames.data.paging.GamePagingSource
import com.example.rawggames.data.remote.network.APIService
import com.example.rawggames.domain.model.Game
import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.domain.repository.GameRepository
import com.example.rawggames.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val gameDao: GameDao,
) : GameRepository {
    override fun getGames(query: String): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GamePagingSource(apiService, query)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toGame()
            }
        }
    }

    override fun getGameDetail(id: Long): Flow<Resource<GameDetail>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getGameDetail(id = id)
            emit(Resource.Success(response.toGameDetail()))
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    emit(Resource.Error("No Internet Connection"))
                }
                else -> {
                    emit(Resource.Error("An unexpected error occurred"))
                }
            }
        }
    }

    override fun saveToFavorite(detail: GameDetail): Flow<Boolean> = flow {
        gameDao.insertGames(detail.toGameEntity())
        emit(true)
    }

    override fun removeFromFavorite(detail: GameDetail): Flow<Boolean> = flow {
        gameDao.deleteGames(detail.toGameEntity())
        emit(false)
    }

    override fun getGameFavorite(): Flow<List<Game>> = gameDao.getGames()
        .map {
            it.map { gameEntity ->
                gameEntity.toGame()
            }
        }

    override fun checkIsFavorite(id: Long): Flow<Boolean> = flow {
        val gameEntity = gameDao.getGameById(id)
        emit(gameEntity != null)
    }
}