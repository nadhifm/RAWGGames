package com.example.rawggames.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val released: String?,
    val backgroundImage: String?,
    val genres: List<GenreEntity>,
)
