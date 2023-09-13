package com.example.rawggames.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("released")
    val released: String?,
    @SerializedName("background_image")
    val backgroundImage: String?,
    @SerializedName("developers")
    val developers: List<DeveloperResponse>,
    val genres: List<GenreResponse>,
    @SerializedName("description_raw")
    val descriptionRaw: String
)