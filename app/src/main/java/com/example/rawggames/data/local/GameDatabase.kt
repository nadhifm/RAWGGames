package com.example.rawggames.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rawggames.data.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1
)
@TypeConverters(GenreEntityListConverter::class)
abstract class GameDatabase : RoomDatabase() {
    abstract val gameDao: GameDao

    companion object {
        const val DATABASE_NAME = "games_db"
    }
}