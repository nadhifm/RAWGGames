package com.example.rawggames.data.remote.response

import com.google.gson.annotations.SerializedName

data class DeveloperResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
)