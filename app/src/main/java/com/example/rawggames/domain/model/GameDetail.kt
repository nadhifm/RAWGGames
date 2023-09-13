package com.example.rawggames.domain.model

data class GameDetail (
    val id: Long,
    val name: String,
    val released: String,
    val backgroundImage: String,
    val developers: List<Developer>,
    val genres: List<Genre>,
    val description: String
)