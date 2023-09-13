package com.example.rawggames.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetGamesResponse (
    @SerializedName("results")
    val results: List<GameResponse>,
)