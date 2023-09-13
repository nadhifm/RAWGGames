package com.example.rawggames.data.remote.network

import com.example.rawggames.data.remote.response.GameDetailResponse
import com.example.rawggames.data.remote.response.GetGamesResponse
import com.example.rawggames.utils.Constans.PAGE_SIZE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key: String = "74e0c16fec6a4d15aaee63c2bfc9bd2b",
        @Query("search") query: String = "",
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = PAGE_SIZE,
    ): GetGamesResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Long,
        @Query("key") key: String = "74e0c16fec6a4d15aaee63c2bfc9bd2b",
    ): GameDetailResponse
}