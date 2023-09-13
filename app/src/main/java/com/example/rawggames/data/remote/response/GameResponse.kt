package com.example.rawggames.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameResponse (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("released")
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("genres")
    val genres: List<GenreResponse>,
)