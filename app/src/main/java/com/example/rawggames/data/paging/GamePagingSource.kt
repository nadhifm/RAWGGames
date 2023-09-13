package com.example.rawggames.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rawggames.data.remote.network.APIService
import com.example.rawggames.data.remote.response.GameResponse
import com.example.rawggames.utils.Resource
import java.io.IOException
import javax.inject.Inject

class GamePagingSource @Inject constructor(
    private val apiService: APIService,
    private val query: String = "",
) : PagingSource<Int, GameResponse>() {
    override fun getRefreshKey(state: PagingState<Int, GameResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameResponse> {
        val pageIndex = params.key ?: 1
        return try {
            val apiResponse =
                apiService.getGames(
                    query = query,
                    page = pageIndex
                )
            val listGameResponse = apiResponse.results
            LoadResult.Page(
                data = listGameResponse,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = if (listGameResponse.isEmpty()) null else pageIndex + 1
            )
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    return LoadResult.Error(Exception("No Internet Connection"))
                }
                else -> {
                    return LoadResult.Error(Exception("An unexpected error occurred"))
                }
            }
        }
    }
}