package com.example.rawggames.data.paging

import androidx.paging.PagingSource
import com.example.rawggames.data.remote.network.APIService
import com.example.rawggames.data.remote.response.GameResponse
import com.example.rawggames.data.remote.response.GenreResponse
import com.example.rawggames.data.remote.response.GetGamesResponse
import com.example.rawggames.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GamePagingSourceTest {
    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var apiService: APIService

    private lateinit var gamePagingSource: GamePagingSource

    @Before
    fun setup() {
        gamePagingSource = GamePagingSource(apiService)
    }

    @Test
    fun `reviews paging source refresh - success`() = runTest {
        val listGamesResponse = listOf(
            GameResponse(
                id = 3498,
                name = "Grand Theft Auto V",
                released = "2013-09-17",
                backgroundImage = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
                genres = listOf(
                    GenreResponse(
                        id = 4,
                        name = "Action"
                    )
                )
            ),
            GameResponse(
                id = 3498,
                name = "Grand Theft Auto V",
                released = "2013-09-17",
                backgroundImage = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
                genres = listOf(
                    GenreResponse(
                        id = 4,
                        name = "Action"
                    )
                )
            ),
            GameResponse(
                id = 3498,
                name = "Grand Theft Auto V",
                released = "2013-09-17",
                backgroundImage = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
                genres = listOf(
                    GenreResponse(
                        id = 4,
                        name = "Action"
                    )
                )
            )
        )
        val expectedResult =
            PagingSource.LoadResult.Page(
                data = listGamesResponse,
                prevKey = null,
                nextKey = 2
            )

        Mockito.`when`(apiService.getGames(page = 1)).thenReturn(
            GetGamesResponse(listGamesResponse)
        )

        val result =  gamePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        Mockito.verify(apiService).getGames(page = 1)
        assertEquals(
            expectedResult, result
        )
    }

    @Test
    fun `reviews paging source refresh - failure`() = runTest {
        val error = HttpException(Response.error<Any>(500,
            "Test Server Error".toResponseBody("text/plain".toMediaTypeOrNull())
        ))
        val expectedResult =
            PagingSource.LoadResult.Error<Int, GenreResponse>(Exception("An unexpected error occurred"))

        Mockito.`when`(apiService.getGames(page = 1)).then {
            throw error
        }

        val result = gamePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        Mockito.verify(apiService).getGames(page = 1)
        assertEquals(
            expectedResult.throwable.message, (result as PagingSource.LoadResult.Error).throwable.message
        )
    }
}