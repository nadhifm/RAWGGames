package com.example.rawggames.data.mapper

import com.example.rawggames.data.local.entity.GameEntity
import com.example.rawggames.data.local.entity.GenreEntity
import com.example.rawggames.data.remote.response.DeveloperResponse
import com.example.rawggames.data.remote.response.GameDetailResponse
import com.example.rawggames.data.remote.response.GameResponse
import com.example.rawggames.data.remote.response.GenreResponse
import com.example.rawggames.domain.model.Developer
import com.example.rawggames.domain.model.Game
import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.domain.model.Genre

fun GameResponse.toGame(): Game {
    return Game(
        id = id,
        name = name,
        released = released ?: "-",
        backgroundImage = backgroundImage ?: "",
        genres = genres.map { it.toGenre() },
    )
}

fun GenreResponse.toGenre(): Genre {
    return Genre(id, name)
}

fun DeveloperResponse.toDeveloper(): Developer {
    return Developer(id, name)
}

fun GameDetailResponse.toGameDetail(): GameDetail {
    return GameDetail(
        id = id,
        name = name,
        released = released ?: "",
        backgroundImage = backgroundImage ?: "",
        genres = genres.map { it.toGenre() },
        developers = developers.map { it.toDeveloper() },
        description = descriptionRaw,
    )
}

fun Genre.toGenreEntity(): GenreEntity {
    return GenreEntity(id, name)
}

fun GenreEntity.toGenre(): Genre {
    return Genre(id, name)
}

fun GameEntity.toGame(): Game {
    return Game(
        id = id,
        name = name,
        released = released ?: "-",
        backgroundImage = backgroundImage ?: "",
        genres = genres.map { it.toGenre() },
    )
}


fun GameDetail.toGameEntity(): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        released = released,
        backgroundImage = backgroundImage,
        genres = genres.map { it.toGenreEntity() },
    )
}

