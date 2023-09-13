package com.example.rawggames.domain.model

data class Game (
    val id: Long,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val genres: List<Genre>,
)