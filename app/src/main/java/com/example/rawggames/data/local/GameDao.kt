package com.example.rawggames.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rawggames.data.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM gameentity")
    fun getGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM gameentity WHERE id = :id")
    suspend fun getGameById(id: Long): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(game: GameEntity)

    @Delete
    suspend fun deleteGames(game: GameEntity)
}